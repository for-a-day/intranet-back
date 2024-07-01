package com.server.intranet.schedule.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.intranet.schedule.dto.ScheduleCreateRequestDTO;
import com.server.intranet.schedule.dto.ScheduleListResponseDTO;
import com.server.intranet.schedule.service.ScheduleService;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/app")
@CrossOrigin(origins = "http://localhost:3000")
public class ScheduleController {
	
	@Autowired
	private ScheduleService scheduleService;
	
	@PostMapping("/schedule")
	public ResponseEntity<Map<String, Object>> createSchedule(@RequestBody ScheduleCreateRequestDTO createRequestDTO) {
		scheduleService.createSchedule(createRequestDTO);
		Map<String, Object> response = new HashMap<>();
		response.put("message", "스케줄 생성 완료");
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/schedule/{calendarId}")
	public ResponseEntity<Map<String, Object>> scheduleListByCalendarId(@PathVariable Long calendarId) {
		List<ScheduleListResponseDTO> schdule = scheduleService.scheduleListByCalendarId(calendarId);
		Map<String, Object> response = new HashMap<>();
		response.put("status", "success");
		response.put("schedule", schdule);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("schedule/detail/{scheduleId}")
	public ResponseEntity<Map<String, Object>> detailSchedule(@PathVariable Long scheduleId) {
		List<ScheduleListResponseDTO> responseDTOs = scheduleService.detailSchedule(scheduleId);
		Map<String, Object> response = new HashMap<>();
		if(!responseDTOs.isEmpty()) {
			response.put("status", "success");
			response.put("schedule", responseDTOs);
			return ResponseEntity.ok(response);
		} else {
			response.put("status", "error");
			response.put("message", "Schdule not found");
			return ResponseEntity.status(404).body(response);
		}
	}
	
	@PutMapping("schedule/{scheduleId}")
	public ResponseEntity<Map<String, Object>> updateSchedule(@PathVariable Long scheduleId, @RequestBody  ScheduleCreateRequestDTO requestDTO)  {
		scheduleService.updateSchedule(scheduleId, requestDTO);
		Map<String, Object> response = new HashMap<>();
		response.put("status", "success");
		response.put("message", "schedule update success");
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("schedule/{scheduleId}")
	public ResponseEntity<Map<String, Object>> deleteSchedule(@PathVariable Long scheduleId) {
		scheduleService.deleteSchedule(scheduleId);
		Map<String, Object> response = new HashMap<>();
		response.put("status", "success");
		response.put("message", "schedule delete success");
		return ResponseEntity.ok(response);
	}
	
	
	
	
}
