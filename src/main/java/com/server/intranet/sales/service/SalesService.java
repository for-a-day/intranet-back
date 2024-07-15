package com.server.intranet.sales.service;

import java.util.List;

import com.server.intranet.franchisee.entity.FranchiseeEntity;
import com.server.intranet.sales.dto.SalesReponseDto;
import com.server.intranet.sales.entity.SalesEntity;

public interface SalesService {
	SalesEntity saveSalesData(SalesEntity sales, String storeCode); // api 저장
	List<SalesReponseDto> salesList(); // 목록
	boolean existsByYearAndMonthAndStoreCode(int year, int month, FranchiseeEntity storeCode); //api 중복 체크
}
