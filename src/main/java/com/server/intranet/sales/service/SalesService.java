package com.server.intranet.sales.service;

import java.util.List;

import com.server.intranet.sales.dto.SalesReponseDto;
import com.server.intranet.sales.entity.SalesEntity;

public interface SalesService {
	SalesEntity saveSalesData(SalesEntity sales, String storeCode); // api 저장
	List<SalesReponseDto> salesList(); // 목록
}
