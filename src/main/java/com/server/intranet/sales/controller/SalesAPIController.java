package com.server.intranet.sales.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import com.server.intranet.franchisee.entity.FranchiseeEntity;
import com.server.intranet.sales.entity.SalesEntity;
import com.server.intranet.sales.service.SalesService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class SalesAPIController {
	
	private static final Logger logger = LoggerFactory.getLogger(SalesAPIController.class);
	
	private final WebClient.Builder webClientBuilder;
	private final SalesService salesService;
	
	@Autowired
	public SalesAPIController(WebClient.Builder webClientBuilder, SalesService salesService) {
		this.webClientBuilder = webClientBuilder;
		this.salesService = salesService;
	}
	
	@PostMapping("/go/sales")
	public ResponseEntity<String> saveData(@RequestBody Map<String, Object> requestData){
		try {
			int year = (int) requestData.get("year");
            int month = (int) requestData.get("month");
            
            Object var = saveSalesData(year, month);
            
            System.out.println(var);
            System.out.println("api 구동 시작 : saveData");

            return ResponseEntity.ok("saveSalesData 성공적 실행");
            
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("매출 데이터 저장 중 오류 발생");
		}
	}
	
	//내용 : 년도, 월, 월매출액, 점포코드
	@GetMapping("/sales")
	public ResponseEntity<Map<String, Object>> saveSalesData(@RequestParam int year, @RequestParam int month) {
		System.out.println("saveSalesData 메서드 시작 - " + " year : " +  year + " month : " + month);

	    String uri = UriComponentsBuilder.fromPath("/sales")
	            .queryParam("year", year)
	            .queryParam("month", month)
	            .toUriString();

	    WebClient webClient = webClientBuilder.baseUrl("http://localhost:9001/api").build();
	    
	    System.out.println("webClient까지는 실행됨");
	    
	    try {
		    System.out.println("try까지는 실행됨");
	        Object responseBody = webClient.get()
	                .uri(uri)
	                .retrieve()
	                .bodyToMono(Object.class)
	                .block(); 
	        
		    System.out.println("responseBody까지는 실행됨" + responseBody);
		    if (responseBody instanceof List) {
                System.out.println("조건문 들어옴");
                List<Map<String, Object>> responseList = (List<Map<String, Object>>) responseBody;
                List<Map<String, Object>> salesResponseList = new ArrayList<>();
                
                for (Map<String, Object> responseMap : responseList) {
                	System.out.println("for문 들어옴");
                    int apiYear = (int) responseMap.get("year");
                    int apiMonth = (int) responseMap.get("month");
                    int apiMonthlySales = (int) responseMap.get("monthlySales");
                    String storeCode = (String) responseMap.get("storeCode");
                    
                    System.out.println("api 변환 실행");
                    
                    // 가맹점 정보 설정
                    FranchiseeEntity apiFranchisee = new FranchiseeEntity(); // Assuming FranchiseeEntity has a default constructor
                    apiFranchisee.setFranchiseeId(storeCode);
                    
                    System.err.println("apiFranchisee에 Id : " + storeCode);
                    
                    // SalesEntity 객체 생성 및 데이터 설정
                    SalesEntity sales = new SalesEntity();
                    sales.setYear(apiYear);
                    sales.setMonth(apiMonth);
                    sales.setMonthlySales(apiMonthlySales);
                    sales.setFranchiseeId(apiFranchisee);

                    System.out.println("SalesEntity까지 통과");
                    
                    // 서비스 메서드 호출하여 저장 및 반환된 SalesEntity 받기
                    SalesEntity savedSalesEntity = salesService.saveSalesData(sales, storeCode);

                    System.out.println("저장을 해보겠습니다");
                    
                    // 저장된 SalesEntity의 필요 정보 맵에 담기
                    Map<String, Object> salesMap = new HashMap<>();
                    salesMap.put("year", savedSalesEntity.getYear());
                    salesMap.put("month", savedSalesEntity.getMonth());
                    salesMap.put("monthlySales", savedSalesEntity.getMonthlySales());
                    salesMap.put("franchiseeId", savedSalesEntity.getFranchiseeId());
                    salesMap.put("storeCode", storeCode);
                    salesResponseList.add(salesMap);
                }
		        System.out.println("for문 탈출");
		        
		        // 최종 결과 반환
		        Map<String, Object> response = new HashMap<>();
		        response.put("salesData", salesResponseList);
	            return ResponseEntity.ok(response);
	        } else {
			    System.err.println("유효하지 않은 API");
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
	                    Collections.singletonMap("error", "Invalid response format from API"));
	        }
	    } catch (Exception e) {
	        logger.error("Error processing API response", e);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
	                Collections.singletonMap("error", "Error processing API response"));
	    }
	}

}
