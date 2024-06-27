package com.server.intranet.approval.entity;

import jakarta.persistence.*;
import lombok.*;


/**
 * packageName    : com.server.intranet.approval.entity
 * fileName       : ApprovalFile
 * author         : gradesdc
 * date           : 2024-06-26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-26        gradesdc       최초 생성
 * 전자결재 첨부파일 엔티티 생성
 */
@Entity(name = "APPROVAL_FILE")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ApprovalFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FILE_ID")
    private Long fileId;

    @ManyToOne
    @JoinColumn(name = "APPROVAL_ID")
    private ApprovalElectronic approvalId;

    @Column(name = "APPROVAL_FILE_NAME")
    private String fileName;

    @Column(name = "STORAGE")
    private String storage;


}
