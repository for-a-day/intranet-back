package com.server.intranet.closing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.server.intranet.closing.entity.ClosingEntity;

@Repository
public interface ClosingRepository extends JpaRepository<ClosingEntity, String> {
	// 폐점 목록 화면
	List<ClosingEntity> findAll();
}
