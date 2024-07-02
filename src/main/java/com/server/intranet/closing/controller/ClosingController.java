package com.server.intranet.closing.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.intranet.closing.dto.ClosingRequestDto;
import com.server.intranet.closing.dto.ClosingResponseDto;
import com.server.intranet.closing.entity.ClosingEntity;
import com.server.intranet.closing.service.ClosingService;

@RestController
@RequestMapping("/app")
@CrossOrigin(origins = "http://localhost:3000")
public class ClosingController {
	private final ClosingService closingService;
	
	private static final Logger logger = LoggerFactory.getLogger(ClosingController.class);
	
	public ClosingController(ClosingService closingService) {
		this.closingService = closingService;
	}
	
	@GetMapping("/close")
	public ResponseEntity<Map<String, Object>> closeList(){
		List<ClosingResponseDto> closeList = closingService.closeList();
		Map<String, ClosingResponseDto> closeMap = closeList.stream()
				.collect(Collectors.toMap(ClosingResponseDto::getClosingId,Function.identity()));
		
		Map<String, Object> response = new HashMap<>();
		response.put("data", closeMap);
		response.put("message", "폐점 목록 생성 완료");
		response.put("status", "success");
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/close")
	public ResponseEntity<Map<String, Object>> insert(@RequestBody ClosingRequestDto closingRequestDto){
		try {
			ClosingEntity closing = closingService.insert(closingRequestDto);
			return new ResponseEntity<>(Collections.singletonMap("message", "폐점 등록 : 성공"), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("폐점 등록 중 오류 발생" + e.getMessage(), e);
			return new ResponseEntity<>(Collections.singletonMap("message", "폐점 등록 : 실패"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
