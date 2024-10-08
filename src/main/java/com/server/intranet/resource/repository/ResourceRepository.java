package com.server.intranet.resource.repository;


import com.server.intranet.resource.dto.ResourceRequestDTO;
import com.server.intranet.resource.entity.AuthorityEntity;
import com.server.intranet.resource.entity.DepartmentEntity;
import com.server.intranet.resource.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends JpaRepository<EmployeeEntity, Long> {
}

