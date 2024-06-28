package com.server.intranet.approval.repository;

import com.server.intranet.approval.entity.ApprovalForm;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName    : com.server.intranet.approval.repository
 * fileName       : ApprovalRepository
 * author         : Lee YunJae
 * date           : 2024-06-27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-27        Lee YunJae       최초 생성
 */
public interface ApprovalFormRepository extends JpaRepository<ApprovalForm, Long> {

}
