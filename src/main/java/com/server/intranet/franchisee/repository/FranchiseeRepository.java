package com.server.intranet.franchisee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.server.intranet.franchisee.entity.FranchiseeEntity;

public interface FranchiseeRepository extends JpaRepository<FranchiseeEntity, String> {
	// 가맹점 목록 화면
	List<FranchiseeEntity> findAll();
}


//@Query("SELECT f " +
//"FROM FranchiseeEntity f " +
//"WHERE f.employee_id = :employee")
//List<FranchiseeEntity> findFranchiseesByEmployee(@Param("employee") Long employee);

//// 사원 아이디가 동일한 사람의 사원명 조회
//@Query("SELECT e.NAME " +
//"FROM FranchiseeEntity f " +
//"JOIN f.employee_id e " +
//"WHERE f.employee_id = :employee")
//List<String> findEmployeeNamesByFranchiseeEmployeeId(@Param("employee") Long employee);