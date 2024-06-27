package com.server.intranet.calendar.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.intranet.calendar.dto.CalendarCreateRequestDTO;
import com.server.intranet.calendar.dto.CalendarListResponseDTO;
import com.server.intranet.calendar.entity.CalendarEntity;
import com.server.intranet.calendar.repository.CalendarRepository;
import com.server.intranet.calendar.service.CalendarService;
import com.server.intranet.login.entity.DepartmentEntity;
import com.server.intranet.login.repository.DepartmentRepository;

@Service
public class CalendarServiceImpl implements CalendarService {

	@Autowired
	private CalendarRepository calendarRepository;
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Override
	public CalendarEntity createCalendar(CalendarCreateRequestDTO calendarRequestDTO) {
		
		Long departmentCode = calendarRequestDTO.getDepartmentCode();
		
		DepartmentEntity department = departmentRepository.findById(departmentCode)
				.orElseThrow(() -> new IllegalArgumentException("Invalid department code"));
	
		CalendarEntity calendar = CalendarEntity.builder()
				.CALENDAR_NAME(calendarRequestDTO.getCalendarName())
				.department(department)
				.build();
		
		return calendarRepository.save(calendar);
	}
	
	@Override
	public List<CalendarListResponseDTO> listCalendar() {
		List<CalendarEntity> calendars = calendarRepository.findAll();
		return calendars.stream().map(calendar -> {
			CalendarListResponseDTO ListDTO = new CalendarListResponseDTO();
				ListDTO.setCalendarId(calendar.getCALENDAR_ID());
				ListDTO.setCalendarName(calendar.getCALENDAR_NAME());
				return ListDTO;
		}).collect(Collectors.toList());
	}
}
