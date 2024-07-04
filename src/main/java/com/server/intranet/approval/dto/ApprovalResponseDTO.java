package com.server.intranet.approval.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * packageName    : com.server.intranet.approval.dto
 * fileName       : ApprovalResponseDTO
 * author         : gladious
 * date           : 2024-06-28
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-28        gladious       최초 생성
 */
@Data
@Builder
public class ApprovalResponseDTO {
    private Long approvalId;
    private Long formId;
    private String formName;
    private String fileName;
    private Integer fileCount;
    private String reason;
    private String reasonRejection;
    private String status;
    private String subject;
    private String urgency;
    private String docBody;
    private String category;
    private String approvalType;    //상세페이지 접속 때 기안자/결재자 구분
    private LocalDateTime creationDate;
    private List<ParticipantResponseDTO> participantList;
}
