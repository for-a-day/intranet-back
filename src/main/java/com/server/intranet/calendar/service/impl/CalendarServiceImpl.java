package com.server.intranet.calendar.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.intranet.calendar.dto.CalendarCreateRequestDTO;
import com.server.intranet.calendar.dto.CalendarListResponseDTO;
import com.server.intranet.calendar.dto.CalendarUpdateRequestDTO;
import com.server.intranet.calendar.entity.CalendarEntity;
import com.server.intranet.calendar.repository.CalendarRepository;
import com.server.intranet.calendar.service.CalendarService;
import com.server.intranet.resource.entity.DepartmentEntity;
import com.server.intranet.resource.repository.DepartmentRepository;

import jakarta.annotation.PostConstruct;


@Service
public class CalendarServiceImpl implements CalendarService {

	@Autowired
	private CalendarRepository calendarRepository;
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@PostConstruct
    public void init() {
		 if (departmentRepository.count() == 0) {
	            // 부서 생성
	            List<DepartmentEntity> departments = Arrays.asList(
	                new DepartmentEntity(1L, "사장"),
	                new DepartmentEntity(2L, "인사부"),
	                new DepartmentEntity(3L, "기획부"),
	                new DepartmentEntity(4L, "사업부"),
	                new DepartmentEntity(5L, "물류부"),
	                new DepartmentEntity(6L, "전산부")
	            );
	            departmentRepository.saveAll(departments);
	        }

        // 캘린더 생성
        List<DepartmentEntity> departments = departmentRepository.findAll();
        List<CalendarEntity> calendarsToCreate = departments.stream()
            .filter(department -> calendarRepository.findByDepartment_DepartmentCode(department.getDepartmentCode()).isEmpty())
            .map(department -> CalendarEntity.builder()
                .CALENDAR_NAME(department.getDepartmentName() + " 업무 일정")
                .department(department)
                .build()
            ).collect(Collectors.toList());

        if (!calendarsToCreate.isEmpty()) {
            calendarRepository.saveAll(calendarsToCreate);
        }
    }

	
	
	
	
	
	
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
	
	/*
	 * @Override public List<CalendarListResponseDTO> listCalendar() {
	 * List<CalendarEntity> calendars = calendarRepository.findAll(); return
	 * calendars.stream().map(calendar -> { CalendarListResponseDTO ListDTO = new
	 * CalendarListResponseDTO(); ListDTO.setCalendarId(calendar.getCALENDAR_ID());
	 * ListDTO.setCalendarName(calendar.getCALENDAR_NAME()); return ListDTO;
	 * }).collect(Collectors.toList()); }
	 */
	
	@Override
	public List<CalendarListResponseDTO> calendarByDepartment(Long department) {
		List<CalendarEntity> calendars= calendarRepository.findByDepartment_DepartmentCode(department);
		return calendars.stream().map(calendar -> new CalendarListResponseDTO(
				calendar.getCALENDAR_ID(),
				calendar.getCALENDAR_NAME(),
				calendar.getDepartment().getDepartmentCode()
				)).collect(Collectors.toList());
	}
	
	@Override
	public Integer updateCalendar(Long calendarId, CalendarUpdateRequestDTO updateRequestDTO) {
		CalendarEntity calendar = calendarRepository.findById(calendarId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid calendar ID"));
		DepartmentEntity department = departmentRepository.findById(updateRequestDTO.getDepartmentCode())
				.orElseThrow(() -> new IllegalArgumentException("Invalid  department Code"));
		
		calendar.setCALENDAR_NAME(updateRequestDTO.getCalendarName());
		calendar.setDepartment(department);
		
		calendarRepository.save(calendar);
		return calendar.getCALENDAR_ID().intValue();
	}
	
	@Override
	public void deleteCalendar(Long calendarId) {
		CalendarEntity calendar = calendarRepository.findById(calendarId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid calendar ID"));
		calendarRepository.delete(calendar);
	}
	
}
