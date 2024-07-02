package com.server.intranet.approval.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * packageName    : com.server.intranet.approval.dto
 * fileName       : ApprovalParticipantDTO
 * author         : gladious
 * date           : 2024-07-01
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-01        gladious       최초 생성
 */
@Data
@Builder
public class ParticipantResponseDTO {
    private Long participantId;
    private Long approvalId;
    private Long employeeId;
    private Integer seq;
    private String type;
    private String status;
    private LocalDateTime approvalDate;
    private String employeeName;
    private Long departmentCode;
    private String departmentName;
}
