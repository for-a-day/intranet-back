package com.server.intranet.calendar.service;

import java.util.List;

import com.server.intranet.calendar.dto.CalendarCreateRequestDTO;
import com.server.intranet.calendar.dto.CalendarListResponseDTO;
import com.server.intranet.calendar.dto.CalendarUpdateRequestDTO;
import com.server.intranet.calendar.entity.CalendarEntity;

public interface CalendarService {
	public CalendarEntity createCalendar(CalendarCreateRequestDTO calendarRequestDTO);
	/* public List<CalendarListResponseDTO> listCalendar(); */
	public List<CalendarListResponseDTO> calendarByDepartment(Long department);
	public Integer updateCalendar(Long calendarId, CalendarUpdateRequestDTO updateRequestDTO);
	public void deleteCalendar(Long calendarId);
}
