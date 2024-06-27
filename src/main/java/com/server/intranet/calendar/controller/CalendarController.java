package com.server.intranet.calendar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.intranet.calendar.dto.CalendarCreateRequestDTO;
import com.server.intranet.calendar.dto.CalendarListResponseDTO;
import com.server.intranet.calendar.entity.CalendarEntity;
import com.server.intranet.calendar.service.CalendarService;

@RestController
@RequestMapping("/app")
@CrossOrigin(origins = "http://localhost:3000")
public class CalendarController {

	@Autowired
	private CalendarService calendarService;
	
	@GetMapping("/calendar")
	public List<CalendarListResponseDTO> listCalender() {
		return calendarService.listCalendar();
	}
	
	@PostMapping("/calendar")
	public CalendarEntity createCalendar(@RequestBody CalendarCreateRequestDTO calendarRequestDTO) {
		return calendarService.createCalendar(calendarRequestDTO);
	}
}
