package com.server.intranet.schedule.service;

import java.util.List;

import com.server.intranet.schedule.dto.ScheduleCreateRequestDTO;
import com.server.intranet.schedule.dto.ScheduleListResponseDTO;

public interface ScheduleService {
	public void createSchedule(ScheduleCreateRequestDTO createRequestDTO);
	public List<ScheduleListResponseDTO> scheduleListByCalendarId(Long calendarId);
}
