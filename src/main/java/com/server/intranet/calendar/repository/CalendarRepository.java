package com.server.intranet.calendar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.server.intranet.calendar.entity.CalendarEntity;
import com.server.intranet.resource.entity.DepartmentEntity;



@Repository
public interface CalendarRepository extends JpaRepository<CalendarEntity, Long>{
	List<CalendarEntity> findByDepartment_DepartmentCode(Long department);
}
