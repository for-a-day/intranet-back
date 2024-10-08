package com.server.intranet.franchisee.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.server.intranet.franchisee.entity.FranchiseeEntity;

@Repository
public interface FranchiseeRepository extends JpaRepository<FranchiseeEntity, String> {
	// 가맹점 목록 화면
	List<FranchiseeEntity> findAll();
	Optional<FranchiseeEntity> findByFranchiseeId(FranchiseeEntity franchisee_Id);
	FranchiseeEntity findByFranchiseeId(String franchiseeId);
	@Query("SELECT f.franchiseeId FROM FranchiseeEntity f WHERE f.franchiseeId NOT IN (SELECT s.franchiseeId.franchiseeId FROM SalesEntity s)")
	List<String> findFranchiseeIdsNotInSales();
}
