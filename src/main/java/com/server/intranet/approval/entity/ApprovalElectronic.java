package com.server.intranet.approval.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@EntityListeners(AuditingEntityListener.class)
public class ApprovalElectronic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "APPROVAL_ID")
    private Long approvalId;

    @ManyToOne
    @JoinColumn(name = "FORM_ID")
    private ApprovalForm formId;

    //제목
    @Column(name = "SUBJECT", nullable = false)
    private String subject;

    //내용
    @Column(name = "DOC_BODY", nullable = false, columnDefinition="TEXT")
    private String doc_body;

    //임시내용
    @Column(name = "TEMP_BODY", nullable = false, columnDefinition="TEXT")
    private String temp_body;

    //결재 사유
    @Column(name = "REASON")
    private String reason;

    //반려 사유
    @Column(name = "REASON_REJECTION")
    private String rejection;

    //긴급
    @Column(name = "URGENCY")
    private String urgency;

    // 처리상태
    @Column(name = "STATUS")
    private String status;

    //결재문서번호
    @Column(name = "DOC_NO")
    private Integer docNo;

    @CreatedDate
    @Column(name = "CREATION_DATE")
    private LocalDateTime creationDate;

    @LastModifiedDate
    @Column(name = "MODIFICATION_DATE")
    private LocalDateTime modificationDate;
}
