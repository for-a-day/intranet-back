package com.server.intranet.resource.repository;

import com.server.intranet.resource.entity.DepartmentEntity;
import com.server.intranet.resource.entity.LevelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {
}

