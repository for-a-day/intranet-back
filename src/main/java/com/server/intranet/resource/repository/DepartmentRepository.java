package com.server.intranet.resource.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.intranet.resource.entity.DepartmentEntity;



public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long>{

}
