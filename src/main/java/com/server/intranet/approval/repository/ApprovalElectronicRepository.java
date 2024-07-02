package com.server.intranet.approval.repository;

import com.server.intranet.approval.entity.ApprovalElectronic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

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
    //기안 문서함
    @Query(value = "SELECT e.* FROM electronic_approval e JOIN approval_participant a ON e.approval_id = a.approval_id WHERE a.employee_id  = :employeeId AND a.type = :type;" , nativeQuery = true)
    List<ApprovalElectronic> findByApprovalIdAndDraft(@Param("employeeId") Long employeeId, @Param("type") String type );


}
