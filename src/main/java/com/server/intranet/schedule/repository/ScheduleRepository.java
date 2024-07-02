package com.server.intranet.schedule.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.intranet.calendar.entity.CalendarEntity;
import com.server.intranet.schedule.entity.ScheduleEntity;


public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long>{
	List<ScheduleEntity> findByCalendar(CalendarEntity calendar);
}
