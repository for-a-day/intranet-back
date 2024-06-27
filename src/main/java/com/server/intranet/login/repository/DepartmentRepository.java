package com.server.intranet.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.intranet.login.entity.DepartmentEntity;

public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long>{

}
