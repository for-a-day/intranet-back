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
    @Query(value = "SELECT e.* FROM electronic_approval e JOIN approval_participant a ON e.approval_id = a.approval_id WHERE a.employee_id  = :employeeId AND a.type = :type AND e.status != 'T';" , nativeQuery = true)
    List<ApprovalElectronic> findByApprovalIdAndDraft(@Param("employeeId") Long employeeId, @Param("type") String type );

    //임시/결재 문서함
    @Query(value = "SELECT e.* FROM electronic_approval e JOIN approval_participant a ON e.approval_id = a.approval_id WHERE e.status = :status AND a.employee_id  = :employeeId AND a.type = :type;" , nativeQuery = true)
    List<ApprovalElectronic> findByApprovalIdAndTA( @Param("status")String status, @Param("employeeId") Long employeeId, @Param("type") String type);

    //결재 대기함
    @Query(value = "SELECT e.* FROM electronic_approval e JOIN approval_participant a ON e.approval_id = a.approval_id WHERE e.status = a.sequence AND a.employee_id  = :employeeId AND a.type = :type;" , nativeQuery = true)
    List<ApprovalElectronic> findByApprovalIdAndTodo( @Param("employeeId") Long employeeId, @Param("type") String type);

    //결재 예정 대기함
    @Query(value = "SELECT e.* FROM electronic_approval e JOIN approval_participant a ON e.approval_id = a.approval_id WHERE e.status REGEXP '^[0-9]+$' AND a.sequence REGEXP '^[0-9]+$' AND e.status < a.sequence AND a.employee_id = :employeeId AND a.type = :type;" , nativeQuery = true)
    List<ApprovalElectronic> findByApprovalIdAndSchedule( @Param("employeeId") Long employeeId, @Param("type") String type);

//    //부서 문서함
//    @Query(value = "SELECT e.* FROM electronic_approval e JOIN approval_participant a ON e.approval_id = a.approval_id WHERE e.status = a.sequence AND a.employee_id  = :employeeId;" , nativeQuery = true)
//    List<ApprovalElectronic> findByApprovalIdAndDepartment(@Param("employeeId") Long employeeId);
}
