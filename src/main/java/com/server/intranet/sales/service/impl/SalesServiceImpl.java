package com.server.intranet.sales.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.intranet.franchisee.entity.FranchiseeEntity;
import com.server.intranet.franchisee.repository.FranchiseeRepository;
import com.server.intranet.franchisee.service.FranchiseeService;
import com.server.intranet.sales.dto.SalesReponseDto;
import com.server.intranet.sales.entity.SalesEntity;
import com.server.intranet.sales.repository.SalesRepository;
import com.server.intranet.sales.service.SalesService;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
public class SalesServiceImpl implements SalesService {
	private final SalesRepository salesRepository;
	
    private FranchiseeRepository franchiseeRepository;
    private FranchiseeService franchiseeService;
	private EntityManager entityManager;
	
    @Autowired
	public SalesServiceImpl(SalesRepository salesRepository, FranchiseeRepository franchiseeRepository, EntityManager entityManager, FranchiseeService franchiseeService) {
		this.salesRepository = salesRepository;
		this.franchiseeRepository = franchiseeRepository;
		this.entityManager = entityManager;
		this.franchiseeService = franchiseeService;
	}
	
	// 매출 api 저장하는 메서드
	@Override
	@Transactional
	public SalesEntity saveSalesData(SalesEntity sales, String storeCode) {
		System.err.println("서비스 단입니다");
		
		// 가맹점 정보 조회
		FranchiseeEntity franchiseeId = franchiseeRepository.findByFranchiseeId(storeCode);
		
		// SalesEntity에 저장된 가맹점 정보 설정
		sales.setFranchiseeId(franchiseeId);
		
		System.err.println("SalesEntity에 저장된 가맹점 정보 설정");
		
		// 매출 데이터 저장
		if(entityManager.contains(sales)) {
			entityManager.merge(sales);  // 이미 영속 상태인 경우
		}else {
			salesRepository.save(sales); // 새로운 엔터티인 경우
		}
		
		System.err.println("성공 => 반환시작");
	    
	    return sales;
	}
	
	// api 중복 체크
	@Override
	public boolean existsByYearAndMonthAndStoreCode(int year, int month, FranchiseeEntity storeCode) {
        return salesRepository.existsByYearAndMonthAndFranchiseeId(year, month, storeCode);
    }
	
	// 목록
	@Override
	public List<SalesReponseDto> salesList() {
		return salesRepository.findAll().stream()
				.map(sales -> new SalesReponseDto(
						sales.getSalesId(),
						sales.getMonthlySales(),
						sales.getYear(),
						sales.getMonth(),
						sales.getFranchiseeId()
						))
						.collect(Collectors.toList());
	}

}
