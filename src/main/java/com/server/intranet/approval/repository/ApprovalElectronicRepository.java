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
    //인트라넷 메인 페이지 내가 올린 진행중인 기안문 수
    @Query(value = "SELECT COUNT(*) FROM electronic_approval e JOIN approval_participant a ON e.approval_id = a.approval_id WHERE a.employee_id  = :employeeId AND a.type = '기안' AND e.status NOT IN ('T', 'C', 'R', 'H');" , nativeQuery = true)
    Integer findByDraftCount(@Param("employeeId") Long employeeId);

    //인트라넷 메인 페이지 내가 올린 기안문 조회
    @Query(value = "SELECT e.* FROM electronic_approval e JOIN approval_participant a ON e.approval_id = a.approval_id WHERE a.employee_id  = :employeeId AND a.type = '기안' AND e.status != 'T' AND e.status != 'H' ORDER BY e.approval_id DESC LIMIT 5;" , nativeQuery = true)
    List<ApprovalElectronic> findByDraft(@Param("employeeId") Long employeeId);

    //기안 문서함
    @Query(value = "SELECT e.* FROM electronic_approval e JOIN approval_participant a ON e.approval_id = a.approval_id WHERE a.employee_id  = :employeeId AND a.type = :type AND e.status != 'T' AND e.status != 'H' ORDER BY e.approval_id DESC;" , nativeQuery = true)
    List<ApprovalElectronic> findByApprovalIdAndDraft(@Param("employeeId") Long employeeId, @Param("type") String type );

    //임시/결재 문서함
    @Query(value = "SELECT e.* FROM electronic_approval e JOIN approval_participant a ON e.approval_id = a.approval_id WHERE e.status = :status AND a.employee_id  = :employeeId AND a.type = :type ORDER BY e.approval_id DESC;" , nativeQuery = true)
    List<ApprovalElectronic> findByApprovalIdAndTA( @Param("status")String status, @Param("employeeId") Long employeeId, @Param("type") String type);

    //결재 대기함
    @Query(value = "SELECT e.* FROM electronic_approval e JOIN approval_participant a ON e.approval_id = a.approval_id WHERE e.status = a.sequence AND a.employee_id  = :employeeId AND a.type = :type ORDER BY e.approval_id DESC;" , nativeQuery = true)
    List<ApprovalElectronic> findByApprovalIdAndTodo( @Param("employeeId") Long employeeId, @Param("type") String type);

    //결재 예정 대기함
    @Query(value = "SELECT e.* FROM electronic_approval e JOIN approval_participant a ON e.approval_id = a.approval_id WHERE e.status REGEXP '^[0-9]+$' AND a.sequence REGEXP '^[0-9]+$' AND e.status < a.sequence AND a.employee_id = :employeeId AND a.type = :type ORDER BY e.approval_id DESC;" , nativeQuery = true)
    List<ApprovalElectronic> findByApprovalIdAndSchedule( @Param("employeeId") Long employeeId, @Param("type") String type);
    
    //결재 진행 문서함(작업중)
    @Query(value = "SELECT e.* FROM electronic_approval e JOIN approval_participant a ON e.approval_id = a.approval_id WHERE e.status != 'C' AND a.employee_id = :employeeId AND a.type = :type AND approval_status = '승인' ORDER BY e.approval_id DESC;" , nativeQuery = true)
    List<ApprovalElectronic> findByApprovalIdAndProcess( @Param("employeeId") Long employeeId, @Param("type") String type);

    //전자결재 메인 페이지 내가 처리해야할 결재 대기(오름차순)
    @Query(value = "SELECT DISTINCT e.* FROM electronic_approval e JOIN approval_participant a ON e.approval_id = a.approval_id WHERE e.status = a.sequence AND a.employee_id = :employeeId AND a.type = '결재' ORDER BY e.urgency DESC, e.approval_id LIMIT 5;" , nativeQuery = true)
    List<ApprovalElectronic> findByMainAndTodo(@Param("employeeId") Long employeeId);

    //전자결재 메인 페이지 내가 올린 기안문 중 진행중 상태(내림차순)
    @Query(value = "SELECT e.* FROM electronic_approval e JOIN approval_participant a ON e.approval_id = a.approval_id WHERE a.employee_id = :employeeId AND a.type = '기안' AND e.status NOT IN ('T', 'C', 'R', 'H') ORDER BY e.approval_id DESC LIMIT 5;" , nativeQuery = true)
    List<ApprovalElectronic> findByMainAndDraftProgress(@Param("employeeId") Long employeeI);

    //전자결재 메인 페이지 내가 올린 기안문 중 완료되 상태(내림차순)
    @Query(value = "SELECT e.* FROM electronic_approval e JOIN approval_participant a ON e.approval_id = a.approval_id WHERE a.employee_id = :employeeId AND a.type = '기안' AND e.status IN ('C') ORDER BY e.approval_id DESC LIMIT 5;" , nativeQuery = true)
    List<ApprovalElectronic> findByMainAndDraftComplete(@Param("employeeId") Long employeeI);

    //기안 번호 최고 값 구하기
    @Query(value = "SELECT MAX(e.doc_no) FROM electronic_approval e" , nativeQuery = true)
    Integer maxDocNo();

//    //부서 문서함
//    @Query(value = "SELECT e.* FROM electronic_approval e JOIN approval_participant a ON e.approval_id = a.approval_id WHERE e.status = a.sequence AND a.employee_id  = :employeeId;" , nativeQuery = true)
//    List<ApprovalElectronic> findByApprovalIdAndDepartment(@Param("employeeId") Long employeeId);
}
