package com.server.intranet.schedule.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.intranet.calendar.entity.CalendarEntity;
import com.server.intranet.calendar.repository.CalendarRepository;
import com.server.intranet.schedule.dto.ScheduleCreateRequestDTO;
import com.server.intranet.schedule.dto.ScheduleListResponseDTO;
import com.server.intranet.schedule.entity.ScheduleEntity;
import com.server.intranet.schedule.repository.ScheduleRepository;
import com.server.intranet.schedule.service.ScheduleService;

@Service
public class ScheduleServiceImpl implements ScheduleService{

	@Autowired
	private ScheduleRepository scheduleRepository;
	
	@Autowired
	private CalendarRepository calendarRepository;
	
	@Override
	public void createSchedule(ScheduleCreateRequestDTO createRequestDTO) {
		CalendarEntity calendar = calendarRepository.findById(createRequestDTO.getCalendarId())
				.orElseThrow(() -> new IllegalArgumentException("Invalid calendar ID"));
		
		ScheduleEntity schedule = ScheduleEntity.builder()
				.calendar(calendar)
				.SUBJECT(createRequestDTO.getSubject())
				.CONTENT(createRequestDTO.getContent())
				.START_DATE(createRequestDTO.getStartDate())
				.END_DATE(createRequestDTO.getEndDate())
				.START_TIME(createRequestDTO.getStartTime())
				.END_TIME(createRequestDTO.getEndTime())
				.LOCATION(createRequestDTO.getLocation())
				.build();
		scheduleRepository.save(schedule);
	}
	
	@Override
	public List<ScheduleListResponseDTO> listSchedule() {
		List<ScheduleEntity> schedules = scheduleRepository.findAll();
		return schedules.stream().map(schedule -> new ScheduleListResponseDTO(
				schedule.getSCHEDULE_ID(),
				schedule.getCalendar().getCALENDAR_ID(),
				schedule.getSUBJECT(),
				schedule.getCONTENT(),
				schedule.getSTART_DATE(),
				schedule.getEND_DATE(),
				schedule.getSTART_TIME(),
				schedule.getEND_TIME(),
				schedule.getLOCATION()
		)).collect(Collectors.toList());
	}
}
