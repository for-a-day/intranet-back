package com.server.intranet.order.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.server.intranet.franchisee.entity.FranchiseeEntity;
import com.server.intranet.menu.entity.MenuEntity;
import com.server.intranet.order.entity.OrderEntity;
import com.server.intranet.order.service.OrderService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderAPIController {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderAPIController.class);
	
	private final WebClient.Builder webClientBuilder;
	private final OrderService orderService;
	
	@Autowired
	public OrderAPIController(WebClient.Builder webClientBuilder, OrderService orderService) {
		this.webClientBuilder = webClientBuilder;
		this.orderService = orderService;
	}
	
	// 주문 api 발동 메서드
	@PostMapping("/go/order")
	public ResponseEntity<String> saveOrder(@RequestBody Map<String, Object> requestData){
		try {
			System.err.println("saveOrder 구동 시작 => api 고고");
			// requestData를 확인하여 원하는 데이터가 들어왔는지 검증
            if (requestData != null && requestData.containsKey("data")) {
                int data = (int) requestData.get("data");
                
                // 원하는 데이터와 매칭되는 조건을 확인 후 saveOrderData 메서드 실행
                if (data == 1) {
                    Object goal = saveOrderData();
                    return ResponseEntity.ok("saveOrderData 성공적 실행 : DB 확인");
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("올바르지 않은 데이터");
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("요청된 데이터가 올바르지 않음");
            }
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("주문 데이터 저장 중 오류 발생");
		}
	}
	
	// 주문 아이디, 주문 수량, 총량, 주문 날짜, 가맹점 아이디(참조), 메뉴 아이디(참조)
	@GetMapping("/order")
	public ResponseEntity<Map<String, Object>> saveOrderData(){
		System.err.println("saveOrderData 컨트롤러 시작합니다");
		
		String uri = UriComponentsBuilder.fromPath("/order").toUriString();
		
		WebClient webClient = webClientBuilder.baseUrl("http://localhost:9001/api").build();
		
		System.out.println("webClient 실행됨");
		
		try {
			System.out.println("try-catch까지 들어옴");
			Object response = webClient.get()
					.uri(uri)
					.retrieve()
					.bodyToMono(Object.class)
					.block();
			
			System.err.println("response까지 실행 => " + response);
			
			if(response instanceof List) {
				System.out.println("if문까지 들어옴");
				
				List<Map<String, Object>> responseList = (List<Map<String, Object>>) response;
				List<Map<String, Object>> orderResponseList = new ArrayList<>();
				
				for(Map<String, Object> responseMap : responseList) {
					System.err.println("for문으로 들어왔슴둥");
						//String apiId = (String) responseMap.get("order_id");
						int apiQuantity = (int) responseMap.get("quantity");
						int apiPrice = (int) responseMap.get("price");
						
						String dateString = (String) responseMap.get("orderDate");
						OffsetDateTime offsetDateTime = OffsetDateTime.parse(dateString, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
						LocalDate date = offsetDateTime.toLocalDate();
						Date apiDate = Date.valueOf(date);
						
						// 먼저 franchisee_id, menu_id는 String으로 받은 후 변환
						String storeCode = (String) responseMap.get("storeCode");
						String menuCode = (String) responseMap.get("menuId");
					
					System.out.println("가맹점, 메뉴 정보 설정");
						// 가맹점, 메뉴 정보 설정
						FranchiseeEntity franchiseeEntity = new FranchiseeEntity();
						MenuEntity menuEntity = new MenuEntity();
						
						franchiseeEntity.setFranchiseeId(storeCode);
						menuEntity.setMenu_id(menuCode);
					
					System.err.println("franchiseeEntity의 id : " + storeCode);	
					System.err.println("menuEntity의 id : " + menuCode);	
						// order 엔티티 객체 생성 및 데이터 설정
						OrderEntity order = new OrderEntity();
						//order.setOrder_id(apiId);
						order.setOrder_quantity(apiQuantity);
						order.setOrder_price(apiPrice);
						order.setOrder_date(apiDate);
						order.setFranchisee_id(franchiseeEntity);
						order.setMenu_id(menuEntity);
						
					System.out.println("OrderEntity까지 통과");
						OrderEntity savedOrderEntity = orderService.saveOrderData(order, storeCode, menuCode);
						
					System.err.println("저장까지 진행");
					 	Map<String, Object> orderMap = new HashMap<>();
					 	//orderMap.put("order_id", savedOrderEntity.getOrder_id());
					 	orderMap.put("order_quantity", savedOrderEntity.getOrder_quantity());
					 	orderMap.put("order_price", savedOrderEntity.getOrder_price());
					 	orderMap.put("order_date", savedOrderEntity.getOrder_date());
					 	orderMap.put("franchisee_id", savedOrderEntity.getFranchisee_id());
					 	orderMap.put("menu_id", savedOrderEntity.getMenu_id());
					 	orderResponseList.add(orderMap);
				}
					System.err.println("for문 탈출");
					
					// 최종 결과 반환
					Map<String,Object> result = new HashMap<>();
					result.put("orderData", orderResponseList);
					return ResponseEntity.ok(result);
			}else {
				System.err.println("유효하지 않은 API");
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
	                    Collections.singletonMap("error", "API에 대한 적절치 못한 응답"));
			}			
		} catch (Exception e) {
			logger.error("Error processing saveOrderData API response", e);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
	                Collections.singletonMap("error", "Error processing API response"));
		}
	}
	
}
