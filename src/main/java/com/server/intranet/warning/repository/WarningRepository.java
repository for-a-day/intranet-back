package com.server.intranet.warning.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.server.intranet.franchisee.entity.FranchiseeEntity;
import com.server.intranet.warning.entity.WarningEntity;

public interface WarningRepository extends JpaRepository<WarningEntity, Long> {
	List<WarningEntity> findAll();
	
	Optional<WarningEntity> findByFranchiseeId(FranchiseeEntity franchisee_Id);
	
	//boolean existsByFranchiseeId(FranchiseeEntity franchisee_Id);
}
