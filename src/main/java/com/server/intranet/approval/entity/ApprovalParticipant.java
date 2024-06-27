package com.server.intranet.approval.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;


/**
 * packageName    : com.server.intranet.approval.entity
 * fileName       : ApprovalParticipant
 * author         : gradesdc
 * date           : 2024-06-26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-26        gradesdc       최초 생성
 *                                전자결재 결재/기안/합의자 관련 엔티티 생성
 */
@Entity(name = "APPROVAL_PARTICIPANT")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ApprovalParticipant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PARTICIPANT_ID")
    private Long participantId;

    @ManyToOne
    @JoinColumn(name = "APPROVAL_ID")
    private ApprovalElectronic approvalId;
    
//    사원테이블 생성 시 추가할 예정
//    @ManyToOne
//    @JoinColumn(name = "EMPLOYEE_ID")
//    private ApprovalForm formId;

    @Column(name = "SEQUENCE")
    private Integer seq;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "APPROVAL_STATUS")
    private String status;

    @LastModifiedDate
    @Column(name = "APPROVAL_DATE")
    private LocalDateTime approvalDate;
}
