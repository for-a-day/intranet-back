package com.server.intranet.franchisee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.server.intranet.franchisee.entity.FranchiseeEntity;

@Repository
public interface FranchiseeRepository extends JpaRepository<FranchiseeEntity, String> {
	// 가맹점 목록 화면
	List<FranchiseeEntity> findAll();
}
