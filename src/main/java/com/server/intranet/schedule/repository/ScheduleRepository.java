package com.server.intranet.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.intranet.schedule.entity.ScheduleEntity;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long>{
	
}
