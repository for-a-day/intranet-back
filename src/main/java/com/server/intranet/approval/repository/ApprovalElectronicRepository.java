package com.server.intranet.approval.repository;

import com.server.intranet.approval.entity.ApprovalElectronic;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName    : com.server.intranet.approval.repository
 * fileName       : ApprovalElectronicRepository
 * author         : gladious
 * date           : 2024-06-28
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-28        gladious       최초 생성
 */
public interface ApprovalElectronicRepository extends JpaRepository<ApprovalElectronic, Long> {
}
