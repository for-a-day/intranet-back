package com.server.intranet.resource.repository;

import com.server.intranet.resource.entity.ExitEmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExitEmployeeRepository extends JpaRepository<ExitEmployeeEntity, Long> {
}
