package com.server.intranet.calendar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.intranet.calendar.entity.CalendarEntity;

public interface CalendarRepository extends JpaRepository<CalendarEntity, Long>{

}
