package com.server.intranet.approval.service.impl;

import com.server.intranet.approval.dto.ApprovalFormResponseDTO;
import com.server.intranet.approval.dto.ParticipantResponseDTO;
import com.server.intranet.approval.dto.ApprovalRequestDTO;
import com.server.intranet.approval.dto.ApprovalResponseDTO;
import com.server.intranet.approval.entity.ApprovalElectronic;
import com.server.intranet.approval.entity.ApprovalForm;
import com.server.intranet.approval.entity.ApprovalParticipant;
import com.server.intranet.approval.entity.Storage;
import com.server.intranet.approval.repository.ApprovalElectronicRepository;
import com.server.intranet.approval.repository.ApprovalFormRepository;
import com.server.intranet.approval.repository.ApprovalParticipantRepository;
import com.server.intranet.approval.repository.StorageRepository;
import com.server.intranet.approval.service.ApprovalService;
import com.server.intranet.resource.entity.EmployeeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * The type Approval service.
 */
@Service
@RequiredArgsConstructor
public class ApprovalServiceImpl implements ApprovalService {
    //양식 폼 레포지토리
    private final ApprovalFormRepository formRepository;
    //저장소 레포지토리
    private final StorageRepository storageRepository;
    //전자개발 레포지토리
    private final ApprovalElectronicRepository approvalRepository;
    //기안자/결재자/합의자 레포지토리
    private final ApprovalParticipantRepository participantRepository;

    /**
     * methodName : selectFormList
     * author : YunJae Lee
     * description : 양식 폼 목록 불러오기
     *
     *
     * @return list
     * @throws Exception the exception
     */
    @Override
    public List<ApprovalFormResponseDTO> selectFormList() throws Exception {
        List<ApprovalForm> list = formRepository.findAll();
        List<ApprovalFormResponseDTO> formList = new ArrayList<>();
        for(ApprovalForm form : list){
            if(!list.isEmpty()){
                ApprovalFormResponseDTO responseDTO = ApprovalFormResponseDTO.builder()
                        .formId(form.getFormId())
                        .subject(form.getSubject())
                        .content(form.getContent())
                        .auth(form.getAuth())
                        .preApproval(form.getPreApproval())
                        .option(form.getOption())
                        .status(form.getStatus())
                        .storageId(form.getStorageId().getStorageId())
                        .storageName(form.getStorageId().getName())
                        .build();
                formList.add(responseDTO);
            }
        }

        return formList;
    }


    /**
     * methodName : selectStorageList
     * author : YunJae Lee
     * description : 저장소 목록 조회
     *
     *
     * @return list
     * @throws Exception the exception
     */
    @Override
    public List<ApprovalFormResponseDTO> selectStorageList() throws Exception {
        List<Storage> list = storageRepository.findAll();
        List<ApprovalFormResponseDTO> storageList = new ArrayList<>();
        for(Storage storage : list){
            if(!list.isEmpty()){
                ApprovalFormResponseDTO responseDTO = ApprovalFormResponseDTO.builder()
                        .storageId(storage.getStorageId())
                        .storageName(storage.getName())
                        .build();
                storageList.add(responseDTO);
            }
        }

        return storageList;
    }


    /**
     * methodName : selectStorageList
     * author : YunJae Lee
     * description : 기안문 등록
     *
     * @Param ApprovalRequestDTO requestDTO
     * @return list
     * @throws Exception the exception
     */
    @Override
    @Transactional
    public ApprovalResponseDTO createApproval(ApprovalRequestDTO requestDTO) throws Exception {
        Long employeeId = 6L;
        ApprovalForm form = formRepository.findById(requestDTO.getFormId()).orElseThrow();
        form.setFormId(requestDTO.getFormId());
        String approvalStatus = "1";
        if(requestDTO.getApprovalInfo().size() == 1){
            approvalStatus = "D";
        }
        ApprovalElectronic approval = ApprovalElectronic.builder()
                .formId(form)
                .subject(requestDTO.getSubject())
                .status(approvalStatus)
                .doc_body(requestDTO.getDocBody())
                .reason(requestDTO.getReason())
                .build();

        ApprovalElectronic response = approvalRepository.save(approval);

        // 기안자 저장(프론트에서 작업하면 기안자를 수정할 수도 있어서 백엔드에서 로직 작업)
        saveApprovalParticipant(response, employeeId, 0, "기안", "D");

        //결재자 저장
        for(Map<String, Object> participant : requestDTO.getApprovalInfo()){
            Long participantEmployeeId = Long.valueOf((Integer) participant.get("employeeId"));
            Integer seq = (Integer) participant.get("seq");
            String type = (String) participant.get("type");
            String status = (String) participant.get("status");

            saveApprovalParticipant(response, participantEmployeeId, seq, type, status);
        }

        return ApprovalResponseDTO.builder()
                .approvalId(response.getApprovalId())
                .subject(response.getSubject())
                .status(response.getStatus())
                .formId(response.getFormId().getFormId())
                .reason(response.getReason())
                .docBody(response.getDoc_body())
                .urgency(response.getUrgency())
                .reasonRejection(response.getRejection())
                .build();
    }

    //참여자 저장 함수
    private void saveApprovalParticipant(ApprovalElectronic approval, Long employeeId, Integer seq, String type, String status) {
        ApprovalParticipant approvalParticipant = ApprovalParticipant.builder()
                .approvalId(approval)
                .employeeId(EmployeeEntity.builder().employeeId(employeeId).build())
                .seq(seq)
                .type(type)
                .status(status)
                .build();
        participantRepository.save(approvalParticipant);
    }

    /**
     * methodName : selectApprovalDetail
     * author : YunJae Lee
     * description : 기안문 상세조회
     *
     * @Param Long approvalId
     * @return list
     * @throws Exception the exception
     */
    @Override
    public ApprovalResponseDTO selectApprovalDetail(Long approvalId) throws Exception {
        //jwt토큰 작업 완료되면 수정 예정
        Long jwtId = 6L;
        String approvalType = "";
        boolean foundType = false;
        ApprovalElectronic response = approvalRepository.findById(approvalId).orElseThrow();
        List<ApprovalParticipant> participantList = participantRepository.findByApprovalId(response);

        return ApprovalResponseDTO.builder()
                .approvalId(response.getApprovalId())
                .subject(response.getSubject())
                .status(response.getStatus())
                .formId(response.getFormId().getFormId())
                .reason(response.getReason())
                .docBody(response.getDoc_body())
                .urgency(response.getUrgency())
                .reasonRejection(response.getRejection())
                .approvalType(approvalType)
                .build();
    }

}
