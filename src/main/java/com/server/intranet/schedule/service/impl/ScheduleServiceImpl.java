package com.server.intranet.schedule.service.impl;

import java.util.List;
import java.util.Optional;
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
	public List<ScheduleListResponseDTO> scheduleListByCalendarId(Long calendarId) {
		CalendarEntity calendar = calendarRepository.findById(calendarId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid calendar ID"));
		List<ScheduleEntity> schedules = scheduleRepository.findByCalendar(calendar);
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
	
	@Override
	public List<ScheduleListResponseDTO> detailSchedule(Long sheduleId) {
		Optional<ScheduleEntity> schedules = scheduleRepository.findById(sheduleId);
		return schedules.stream().map(schedule -> {
			ScheduleListResponseDTO listResponseDTO = new ScheduleListResponseDTO();
			listResponseDTO.setCalendarId(schedule.getCalendar().getCALENDAR_ID());
			listResponseDTO.setScheduleId(schedule.getSCHEDULE_ID());
			listResponseDTO.setSubject(schedule.getSUBJECT());
			listResponseDTO.setContent(schedule.getCONTENT());
			listResponseDTO.setStartDate(schedule.getSTART_DATE());
			listResponseDTO.setEndDate(schedule.getEND_DATE());
			listResponseDTO.setStartTime(schedule.getSTART_TIME());
			listResponseDTO.setEndTime(schedule.getEND_TIME());
			listResponseDTO.setLocation(schedule.getLOCATION());
			return listResponseDTO;
		}).collect(Collectors.toList());
	}
	
	@Override
	public Integer updateSchedule(Long scheduleId, ScheduleCreateRequestDTO requestDTO) {
		ScheduleEntity schedule = scheduleRepository.findById(scheduleId).orElse(null);
		if (schedule != null) {
			schedule.setSUBJECT(requestDTO.getSubject());
			schedule.setCONTENT(requestDTO.getContent());
			schedule.setSTART_DATE(requestDTO.getStartDate());
			schedule.setEND_DATE(requestDTO.getEndDate());
			schedule.setSTART_TIME(requestDTO.getStartTime());
			schedule.setEND_TIME(requestDTO.getEndTime());
			schedule.setLOCATION(requestDTO.getLocation());
			scheduleRepository.save(schedule);
			return 1;
		} else {
			return 0;
		}
	}
		@Override
		public void deleteSchedule(Long scheduleId) {
			ScheduleEntity schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new RuntimeException("schedule not found"));
			scheduleRepository.delete(schedule);
		}
}
