package com.server.intranet.approval.repository;

import com.server.intranet.approval.entity.ApprovalElectronic;
import com.server.intranet.approval.entity.ApprovalParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * packageName    : com.server.intranet.approval.repository
 * fileName       : ApprovalParticipantRepository
 * author         : gladious
 * date           : 2024-06-28
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-28        gladious       최초 생성
 */
public interface ApprovalParticipantRepository extends JpaRepository<ApprovalParticipant, Long> {
    List<ApprovalParticipant> findByApprovalId(ApprovalElectronic approvalId);

    void deleteByApprovalId(ApprovalElectronic response);
}
