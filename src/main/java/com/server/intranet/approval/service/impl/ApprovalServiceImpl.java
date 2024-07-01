package com.server.intranet.approval.service.impl;

import com.server.intranet.approval.dto.ApprovalFormResponseDTO;
import com.server.intranet.approval.dto.ApprovalRequestDTO;
import com.server.intranet.approval.dto.ApprovalResponseDTO;
import com.server.intranet.approval.entity.ApprovalElectronic;
import com.server.intranet.approval.entity.ApprovalForm;
import com.server.intranet.approval.entity.Storage;
import com.server.intranet.approval.repository.ApprovalElectronicRepository;
import com.server.intranet.approval.repository.ApprovalFormRepository;
import com.server.intranet.approval.repository.StorageRepository;
import com.server.intranet.approval.service.ApprovalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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


    @Override
    public ApprovalResponseDTO createApproval(ApprovalRequestDTO requestDTO) throws Exception {
        ApprovalForm form = formRepository.findById(requestDTO.getFormId()).orElseThrow();
        form.setFormId(requestDTO.getFormId());
        ApprovalElectronic approval = ApprovalElectronic.builder()
                .formId(form)
                .subject(requestDTO.getSubject())
                .status("D")
                .doc_body(requestDTO.getDocBody())
                .reason(requestDTO.getReason())
                .build();

        ApprovalElectronic response = approvalRepository.save(approval);
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

    @Override
    public ApprovalResponseDTO selectApprovalDetail(Long approvalId) throws Exception {
        ApprovalElectronic response = approvalRepository.findById(approvalId).orElseThrow();
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


}
