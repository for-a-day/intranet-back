package com.server.intranet.approval.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

/**
 * packageName    : com.server.intranet.approval.entity
 * fileName       : ApprovalElectronic
 * author         : gradesdc
 * date           : 2024-06-26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-26        gradesdc       최초 생성
 *                                전자결재 엔티티 생성
 */
@Entity(name = "ELECTRONIC_APPROVAL")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ApprovalElectronic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "APPROVAL_ID")
    private Long approvalId;

    @ManyToOne
    @JoinColumn(name = "FORM_ID")
    private ApprovalForm formId;

    @Column(name = "SUBJECT", nullable = false)
    private String subject;

    @Column(name = "DOC_BODY", nullable = false, columnDefinition="TEXT")
    private String doc_body;

    @Column(name = "REASON")
    private String reason;

    @Column(name = "REASON_REJECTION")
    private String rejection;

    @Column(name = "URGENCY")
    private String urgency;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "FILE_NAME")
    private String file;

    @CreatedDate
    @Column(name = "CREATION_DATE")
    private LocalDateTime creationDate;

    @LastModifiedDate
    @Column(name = "MODIFICATION_DATE")
    private LocalDateTime modificationDate;
}
