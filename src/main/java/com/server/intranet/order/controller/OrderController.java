package com.server.intranet.order.controller;

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

import com.server.intranet.order.dto.OrderResponseDto;
import com.server.intranet.order.service.OrderService;

@RestController
@RequestMapping("/app")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {
	private final OrderService orderService;
	
	@Autowired
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	// 목록
	@GetMapping("/order")
	public ResponseEntity<Map<String, Object>> orderList(){
		List<OrderResponseDto> orderList = orderService.orderList();
//		Map<Long, Object> orderMap = orderList.stream()
//				.collect(Collectors.toMap(OrderResponseDto :: getOrder_id, Function.identity()));
		Map<String, Object> response = new HashMap<>();
		response.put("data", orderList);
		response.put("message", "주문 목록 생성 완료");
		response.put("status", "success");
		System.err.println("결과 : " + orderList);
		return ResponseEntity.ok(response);
	}
}
