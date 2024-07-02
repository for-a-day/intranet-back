package com.server.intranet.login.repository;

import com.server.intranet.resource.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<EmployeeEntity, Long> {
}
