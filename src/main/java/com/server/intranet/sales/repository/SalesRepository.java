package com.server.intranet.sales.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.intranet.franchisee.entity.FranchiseeEntity;
import com.server.intranet.sales.entity.SalesEntity;

public interface SalesRepository extends JpaRepository<SalesEntity, Long> {
	boolean existsByYearAndMonthAndFranchiseeId(int year, int month, FranchiseeEntity storeCode);
}
