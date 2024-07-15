package com.server.intranet.approval.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * packageName    : com.server.intranet.approval.dto
 * fileName       : ApprovalRequestDTO
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
public class ApprovalRequestDTO {
    private Long approvalId;
    private Long formId;
    private String fileName;
    private String reason;
    private String reasonRejection;
    private String status;
    private String subject;
    private String urgency;
    private String docBody;
    private String tempBody;
    private String saveType;
    private String approvalType;
    private List<Map<String,Object>> approvalInfo;
}
