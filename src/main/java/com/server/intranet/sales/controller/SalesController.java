package com.server.intranet.sales.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.intranet.sales.dto.SalesReponseDto;
import com.server.intranet.sales.service.SalesService;

@RestController
@RequestMapping("/app")
@CrossOrigin(origins = "http://localhost:3000")
public class SalesController {
	
	private final SalesService salesService;
	
	@Autowired
	public SalesController(SalesService salesService) {
		this.salesService = salesService;
	}
	
	// 목록
	@GetMapping("/sales")
	public ResponseEntity<Map<String, Object>> salesList(){
		List<SalesReponseDto> salesList = salesService.salesList();
		Map<String, SalesReponseDto> salesMap = salesList.stream()
				.collect(Collectors.toMap(SalesReponseDto :: getFranchiseeId, Function.identity()));
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("data", salesMap);
		responseMap.put("message", "매출목록 생성 완료");
		responseMap.put("status", "success");
		
		return ResponseEntity.ok(responseMap);
	}
}
