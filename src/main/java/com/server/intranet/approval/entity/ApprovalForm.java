package com.server.intranet.approval.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;


/**
 * packageName    : com.server.intranet.approval.entity
 * fileName       : ApprovalForm
 * author         : gradesdc
 * date           : 2024-06-26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-26        gradesdc       최초 생성
 *                                전자결재 양식 엔티티 생성
 */
@Entity(name = "APPROVAL_FORM")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ApprovalForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FORM_ID")
    private Long formId;

    @Column(name = "SUBJECT", nullable = false)
    private String subject;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "PASSIVE_APPROVAL_OPTION")
    private String option;

    @Column(name = "AUTHORITY")
    private String auth;

    @Column(name = "PRE_APPROVAL")
    private String preApproval;

    @Column(name = "STORAGE")
    private String storage;

    @CreatedDate
    @Column(name = "CREATION_DATE")
    private LocalDateTime creationDate;

    @LastModifiedDate
    @Column(name = "MODIFICATION_DATE")
    private LocalDateTime modificationDate;
}
