package com.server.intranet.sales.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.server.intranet.franchisee.entity.FranchiseeEntity;
import com.server.intranet.sales.entity.SalesEntity;
import com.server.intranet.sales.service.SalesService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class SalesAPIController {
	private final WebClient webClient;
	private final SalesService salesService;
	
	@Autowired
	public SalesAPIController(WebClient.Builder webBuilder, SalesService salesService) {
		this.webClient = webBuilder.baseUrl("http://localhost:9001/api/sales?year={year}&month={month} ").build();
		this.salesService = salesService;
	}
	
	//내용 : 년도, 월, 월매출액, 점포코드
	@GetMapping("/sales")
	public Mono<ResponseEntity<Map<String, Object>>> getSalesData(@RequestParam int year, @RequestParam int month){
		String uri = String.format("/sales?year=%d&month=%d", year, month);
		
		return webClient.get()
			   .uri(uri)
			   .retrieve()
			   .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
			   .flatMap(responsebody -> {
				   
				   int apiYear = (int) responsebody.get("year");
				   int apimonth = (int) responsebody.get("month");
				   int apiMonthlySales = (int) responsebody.get("monthlySales");
				   FranchiseeEntity apiId = (FranchiseeEntity) responsebody.get("franchiseeId");
				   
				   SalesEntity sales = new SalesEntity();
				   sales.setYear(year);
				   sales.setMonth(apimonth);
				   sales.setMonthlySales(apiMonthlySales);
				   sales.setFranchiseeId(apiId);
				   
				   Map<String, Object> salesMap = new HashMap<>();
				   salesMap.put("year", sales.getYear());
				   salesMap.put("month", sales.getMonth());
				   salesMap.put("Monthsales", sales.getMonthlySales());
				   salesMap.put("franchiseeId", sales.getFranchiseeId());
				   
				   return Mono.just(ResponseEntity.ok().body(salesMap));
						   
			   })
			   .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
}
