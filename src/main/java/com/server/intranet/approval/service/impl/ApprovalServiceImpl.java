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
import com.server.intranet.global.util.SecurityUtil;
import com.server.intranet.resource.entity.EmployeeEntity;
import com.server.intranet.resource.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


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
    //인사 레포지토리
    private final ResourceRepository resourceRepository;
    //알림 레포지토리
    private final NotificationServiceImpl notificationServiceImpl;

    //인트라넷 메인 페이지 내가 올린 기안문 조회
    @Override
    public Map<String, Object> selectMainDraftList() throws Exception {
        Long employeeId = Long.valueOf(Objects.requireNonNull(SecurityUtil.getCurrentUserId()));

        List<ApprovalElectronic> list = approvalRepository.findByDraft(employeeId);
        Integer count = approvalRepository.findByDraftCount((employeeId));
        Map<String,Object> data = new HashMap<>();
        data.put("list", list);
        data.put("count", count);

        return data;
    }

    //전자결재 메인 조회
    @Override
    public Map<String, Object> selectApprovalMain() throws Exception {
        Long employeeId = Long.valueOf(Objects.requireNonNull(SecurityUtil.getCurrentUserId()));
        //결재 대기 상태 오래된 순(긴급 있을 시 긴급 우선)
        List<ApprovalElectronic> mainList1 = approvalRepository.findByMainAndTodo(employeeId);
        List<Map<String,Object>> list = new ArrayList<>();
        for(ApprovalElectronic main : mainList1){
            Map<String,Object> main1 = new HashMap<>();
            ApprovalParticipant participant = participantRepository.findByApprovalIdAndType(main, "기안");
            ParticipantResponseDTO employee = ParticipantResponseDTO.builder()
                    .employeeId(participant.getEmployeeId().getEmployeeId())
                    .name(participant.getEmployeeId().getName())
                    .department(participant.getEmployeeId().getDepartment().getDepartmentName())
                    .level(participant.getEmployeeId().getLevel().getLevelName())
                    .build();
            main1.put("list", main);
            main1.put("employee",employee);
            list.add(main1);
        }

        //내 기안함 결재 대기중
        List<ApprovalElectronic> mainList2 = approvalRepository.findByMainAndDraftProgress(employeeId);

        // 내 기안함 결재 완료
        List<ApprovalElectronic> mainList3 = approvalRepository.findByMainAndDraftComplete(employeeId);

        Map<String, Object> map = new HashMap<>();
        map.put("main1", list);
        map.put("main2", mainList2);
        map.put("main3", mainList3);

        return map;
    }

    /**
     * methodName : selectFormList
     * author : YunJae Lee
     * description : 양식 폼 목록 불러오기
     *
     * @return list
     * @throws Exception the exception
     */
    @Override
    public List<ApprovalFormResponseDTO> selectFormList() throws Exception {
        List<ApprovalForm> list = formRepository.findAll();
        List<ApprovalFormResponseDTO> formList = new ArrayList<>();
        for (ApprovalForm form : list) {
            if (!list.isEmpty()) {
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
     * @return list
     * @throws Exception the exception
     */
    @Override
    public List<ApprovalFormResponseDTO> selectStorageList() throws Exception {
        List<Storage> list = storageRepository.findAll();
        List<ApprovalFormResponseDTO> storageList = new ArrayList<>();
        for (Storage storage : list) {
            if (!list.isEmpty()) {
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
     * @return list
     * @throws Exception the exception
     * @Param ApprovalRequestDTO requestDTO
     */
    @Override
    @Transactional
    public ApprovalResponseDTO createApproval(ApprovalRequestDTO requestDTO) throws Exception {
        Long employeeId = Long.valueOf(Objects.requireNonNull(SecurityUtil.getCurrentUserId()));
        Long waitEmployeeId = null;
        ApprovalForm form = formRepository.findById(requestDTO.getFormId()).orElseThrow();
        form.setFormId(requestDTO.getFormId());
        String approvalStatus = "1";
        if (requestDTO.getSaveType().equals("T")) {
            approvalStatus = "T";
        }

        ApprovalElectronic approval = ApprovalElectronic.builder()
                .formId(form)
                .subject(requestDTO.getSubject())
                .status(approvalStatus)
                .doc_body(requestDTO.getDocBody())
                .temp_body(requestDTO.getTempBody())
                .urgency(requestDTO.getUrgency())
                .reason(requestDTO.getReason())
                .build();

        ApprovalElectronic response = approvalRepository.save(approval);

        // 기안자 저장(프론트에서 작업하면 기안자를 수정할 수도 있어서 백엔드에서 로직 작업)
        saveApprovalParticipant(response, employeeId, 0, "기안", "기안");

        //결재자 저장
        for (Map<String, Object> participant : requestDTO.getApprovalInfo()) {
            Long participantEmployeeId = Long.valueOf((Integer) participant.get("employeeId"));
            Integer seq = (Integer) participant.get("seq");
            String type = (String) participant.get("type");
            String status;

            if (seq > 1) {
                status = "예정";
            } else {
                status = (String) participant.get("status");
                waitEmployeeId = participantEmployeeId;
            }

            saveApprovalParticipant(response, participantEmployeeId, seq, type, status);
        }
        if(waitEmployeeId != null && !requestDTO.getSaveType().equals("T")){
            EmployeeEntity employee = resourceRepository.findById(waitEmployeeId).orElseThrow();
            notificationServiceImpl.send(employee, "/approval/draft/detail/"+ response.getApprovalId() + "?a=a", "[ " + response.getSubject() + " ] 결재 요청이 들어왔습니다." );
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
     * methodName : selectApprovalList
     * author : YunJae Lee
     * description : 기안문 목록 조회
     *
     * @return list
     * @throws Exception the exception
     * @Param String category
     */
    @Override
    public List<ApprovalResponseDTO> selectApprovalList(String category) throws Exception {
        //JWT토큰 완료시 수정 예정
        Long employeeId = Long.valueOf(Objects.requireNonNull(SecurityUtil.getCurrentUserId()));
        //employeeId 대신 부서 코드가 들어가야함
        List<ApprovalElectronic> approvalList = new ArrayList<>();
        String approvalType = null;
        switch (category) {
            case "mydraft":
                // 기안 문서함(내가 결재 신청한 기안문 목록)
                approvalList = approvalRepository.findByApprovalIdAndDraft(employeeId, "기안");
                break;
            case "temp":
                // 임시 문서함
                approvalList = approvalRepository.findByApprovalIdAndTA("T", employeeId, "기안");
                break;
            case "complete":
                // 결재 문서함(내가 결재한 문서 중 반려 or 결재 처리 한 문서함)
                approvalList = approvalRepository.findByApprovalIdAndTA("C", employeeId, "결재");
                break;
            case "approval":
                // 추가적인 작업 필요!!!! - 내가 결재를 진행해야 나타나게 해야함? 아니면 나한테 들어온 결재를 완료 했지만 결재 진행중인 상태
                // 결재 완료 문서함(내가 결재한 문서 중 반려 or 결재 처리 한 문서함)
                approvalList = approvalRepository.findByApprovalIdAndProcess(employeeId, "결재");
                break;
            case "todo":
                // 결재 대기 문서함(내가 결재할 문서 중 대기 상태인 문서함)
                approvalList = approvalRepository.findByApprovalIdAndTodo(employeeId, "결재");
                break;
            case "schedule":
                // 결재 예정 문서함(내가 결재할 문서 중 예약 상태인 문서함 - 예약 상태: 내 이전 결재자가 아직 결재 안한 상태)
                approvalList = approvalRepository.findByApprovalIdAndSchedule(employeeId, "결재");
                break;
            case "department":
                // 부서 문서함
//                approvalList = approvalRepository.findByApprovalIdAndDepartment(employeeId, "모든 결재");
                break;
        }

        List<ApprovalResponseDTO> list = new ArrayList<>();
        for (ApprovalElectronic approval : approvalList) {
            if(approval.getStatus().equals("C")){
                approvalType = "완료";
            } else if(approval.getStatus().equals("R")){
                approvalType = "반려";
            } else {
                approvalType = "진행중";
            }
            ApprovalResponseDTO response = ApprovalResponseDTO.builder()
                    .approvalId(approval.getApprovalId())
                    .formId(approval.getFormId().getFormId())
                    .formName(approval.getFormId().getSubject())
                    .subject(approval.getSubject())
                    .fileCount(0)    //file테이블이랑 연돟해서 가져와야 함
                    .status(approval.getStatus())
                    .creationDate(approval.getCreationDate())
                    .modificationDate(approval.getModificationDate())
                    .approvalType(approvalType)
                    .docNo(approval.getDocNo())
                    .urgency(approval.getUrgency())
                    .build();
            list.add(response);
        }
        return list;
    }

    /**
     * methodName : selectApprovalDetail
     * author : YunJae Lee
     * description : 기안문 상세조회
     *
     * @return list
     * @throws Exception the exception
     * @Param Long approvalId
     */
    @Override
    @Transactional
    public ApprovalResponseDTO selectApprovalDetail(Long approvalId, String type) throws Exception {
        Long employeeId = Long.valueOf(Objects.requireNonNull(SecurityUtil.getCurrentUserId()));
        String approvalType = "";
        Integer seq = 0;

        ApprovalElectronic response = approvalRepository.findById(approvalId).orElseThrow();
        List<ApprovalParticipant> participantList = participantRepository.findByApprovalId(response);
        List<ParticipantResponseDTO> list = new ArrayList<>();
        for (ApprovalParticipant participant : participantList) {
            if (participant.getEmployeeId().getEmployeeId().equals(employeeId)) {
                approvalType = participant.getStatus();
                seq = participant.getSeq();
            }
            ParticipantResponseDTO employee;
            if(type != null && type.equals("R")){
                //재기안시 작업
                employee = ParticipantResponseDTO.builder()
                        .participantId(participant.getParticipantId())
                        .employeeId(participant.getEmployeeId().getEmployeeId())
                        .seq(participant.getSeq())
                        .type(participant.getType())
                        .status("대기")
                        .approvalDate(participant.getApprovalDate())
                        .department(participant.getEmployeeId().getDepartment().getDepartmentName())
                        .level(participant.getEmployeeId().getLevel().getLevelName())
                        .name(participant.getEmployeeId().getName())
                        .selectStatus(true)
                        .build();
            } else {
                employee = ParticipantResponseDTO.builder()
                        .participantId(participant.getParticipantId())
                        .employeeId(participant.getEmployeeId().getEmployeeId())
                        .seq(participant.getSeq())
                        .type(participant.getType())
                        .status(participant.getStatus())
                        .approvalDate(participant.getApprovalDate())
                        .department(participant.getEmployeeId().getDepartment().getDepartmentName())
                        .level(participant.getEmployeeId().getLevel().getLevelName())
                        .name(participant.getEmployeeId().getName())
                        .selectStatus(true)
                        .build();
            }

            list.add(employee);
        }

        return ApprovalResponseDTO.builder()
                .approvalId(response.getApprovalId())
                .subject(response.getSubject())
                .status(response.getStatus())
                .formId(response.getFormId().getFormId())
                .reason(response.getReason())
                .docBody(response.getDoc_body())
                .docNo(response.getDocNo())
                .tempBody(response.getTemp_body())
                .urgency(response.getUrgency())
                .creationDate(response.getModificationDate())
                .reasonRejection(response.getRejection())
                .approvalType(approvalType)
                .participantList(list)
                .seq(seq)
                .build();
    }

    //임시보관함에서 수정시
    @Override
    @Transactional
    public ApprovalResponseDTO updateApproval(ApprovalRequestDTO requestDTO) throws Exception {
        Long employeeId = Long.valueOf(Objects.requireNonNull(SecurityUtil.getCurrentUserId()));
        Long waitEmployeeId = null;
        ApprovalForm form = formRepository.findById(requestDTO.getFormId()).orElseThrow();
        form.setFormId(requestDTO.getFormId());
        String approvalStatus = "1";
        if (requestDTO.getSaveType().equals("T")) {
            approvalStatus = "T";
        } else {
            approvalStatus ="1";
        }

        ApprovalElectronic approval = ApprovalElectronic.builder()
                .approvalId(requestDTO.getApprovalId())
                .formId(form)
                .subject(requestDTO.getSubject())
                .status(approvalStatus)
                .doc_body(requestDTO.getDocBody())
                .urgency(requestDTO.getUrgency())
                .temp_body(requestDTO.getTempBody())
                .reason(requestDTO.getReason())
                .build();

        ApprovalElectronic response = approvalRepository.save(approval);
        // 기존 기안자/결재자 삭제
        participantRepository.deleteByApprovalId(response);
        // 기안자 저장(프론트에서 작업하면 기안자를 수정할 수도 있어서 백엔드에서 로직 작업)
        saveApprovalParticipant(response, employeeId, 0, "기안", "기안");

        //결재자 저장
        for (Map<String, Object> participant : requestDTO.getApprovalInfo()) {
            Long participantEmployeeId = Long.valueOf((Integer) participant.get("employeeId"));
            Integer seq = (Integer) participant.get("seq");
            String type = (String) participant.get("type");
            String status;

            if (seq > 1) {
                status = "예정";
            } else {
                status = (String) participant.get("status");
                waitEmployeeId = participantEmployeeId;
            }
            saveApprovalParticipant(response, participantEmployeeId, seq, type, status);
        }

        if(waitEmployeeId != null && !requestDTO.getSaveType().equals("T")){
            EmployeeEntity employee = resourceRepository.findById(waitEmployeeId).orElseThrow();
            notificationServiceImpl.send(employee, "/approval/draft/detail/"+ response.getApprovalId() + "?a=a" , "[ " + response.getSubject() + " ] 결재 요청이 들어왔습니다." );
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

    //참여자 수정 함수
    private void updateApprovalParticipant(Long participantId, ApprovalElectronic approval, Long employeeId, Integer seq, String type, String status) {
        ApprovalParticipant approvalParticipant = ApprovalParticipant.builder()
                .participantId(participantId)
                .approvalId(approval)
                .employeeId(EmployeeEntity.builder().employeeId(employeeId).build())
                .seq(seq)
                .type(type)
                .status(status)
                .build();
        participantRepository.save(approvalParticipant);
    }

    //상신 취소
    @Override
    @Transactional
    public Long updateApprovalCancel(ApprovalRequestDTO requestDTO) throws Exception {
        ApprovalElectronic approval = approvalRepository.findById(requestDTO.getApprovalId()).orElseThrow();
        if(approval.getStatus().equals("1")){
            approval.setStatus("T");
            approval.setDoc_body("docBody");
            return approvalRepository.save(approval).getApprovalId();
        } else {
            return 10L;
        }
    }

    // 결재 및 반려
    @Override
    @Transactional
    public Long updateApprovalRejection(ApprovalRequestDTO requestDTO) throws Exception {
        Long employeeId = Long.valueOf(Objects.requireNonNull(SecurityUtil.getCurrentUserId()));
        ApprovalElectronic approvalDoc = approvalRepository.findById(requestDTO.getApprovalId()).orElseThrow();
        List<ApprovalParticipant> participantList = participantRepository.findByApprovalId(approvalDoc);
        int currentStatus = Integer.parseInt(approvalDoc.getStatus());

        if (requestDTO.getApprovalType().equals("A")) {
            approveParticipants(participantList, employeeId, currentStatus, approvalDoc, requestDTO.getDocBody());
        } else if (requestDTO.getApprovalType().equals("R")) {
            rejectParticipants(participantList, employeeId, approvalDoc, requestDTO.getDocBody(), requestDTO.getReason());
        } else {
            return null;
        }

        return approvalDoc.getApprovalId();
    }

    //승인시 실행될 함수
    private void approveParticipants(List<ApprovalParticipant> participantList, Long employeeId, int currentStatus, ApprovalElectronic approvalDoc, String docBody) throws Exception {
        Long waitEmployeeId = null;
        String content = "";
        for (ApprovalParticipant participant : participantList) {
            if (participant.getEmployeeId().getEmployeeId().equals(employeeId)) {
                participant.setStatus("승인");
            } else if (participant.getSeq() == currentStatus + 1) {
                participant.setStatus("대기");
                waitEmployeeId = participant.getEmployeeId().getEmployeeId();
                content = "[ " + approvalDoc.getSubject() + " ] 결재 요청이 들어왔습니다.";
            }
            participantRepository.save(participant);
        }

        if (currentStatus == (participantList.size() - 1)) {
            approvalDoc.setStatus("C");
            approvalDoc.setDoc_body(docBody);
            Integer maxNo = approvalRepository.maxDocNo();
            if(maxNo == null){
                maxNo = 0;
            }
            approvalDoc.setDocNo(maxNo + 1);
            waitEmployeeId = participantList.get(0).getEmployeeId().getEmployeeId();
            content = "[ " + approvalDoc.getSubject() + " ] 승인 되었습니다.";
        } else if (currentStatus < (participantList.size() - 1)) {
            approvalDoc.setStatus(String.valueOf(currentStatus + 1));
            approvalDoc.setDoc_body(docBody);
        }
        approvalRepository.save(approvalDoc);

        if(waitEmployeeId != null){
            EmployeeEntity employee = resourceRepository.findById(waitEmployeeId).orElseThrow();
            notificationServiceImpl.send(employee, "/approval/draft/detail/"+ approvalDoc.getApprovalId() + "?a=a" , content );
        }
    }

    //반려시 실행될 함수
    private void rejectParticipants(List<ApprovalParticipant> participantList, Long employeeId, ApprovalElectronic approvalDoc, String docBody, String  reason) {
        Long waitEmployeeId = participantList.get(0).getEmployeeId().getEmployeeId();
        String content = "[ " + approvalDoc.getSubject() + " ] 반려 되었습니다.";
        for (ApprovalParticipant participant : participantList) {
            if (participant.getEmployeeId().getEmployeeId().equals(employeeId)) {
                participant.setStatus("반려");
            }
            participantRepository.save(participant);
        }
        approvalDoc.setStatus("R");
        approvalDoc.setDoc_body(docBody);
        approvalDoc.setRejection(reason);
        approvalRepository.save(approvalDoc);

        EmployeeEntity employee = resourceRepository.findById(waitEmployeeId).orElseThrow();
        notificationServiceImpl.send(employee, "/approval/draft/detail/"+ approvalDoc.getApprovalId() + "?a=a" , content );
    }

    //기안문 삭제
    @Override
    @Transactional
    public void deleteApproval(Long id) throws Exception {
        ApprovalElectronic approval = approvalRepository.findById(id).orElseThrow();
        approval.setStatus("H");
        approvalRepository.save(approval);
    }
}
