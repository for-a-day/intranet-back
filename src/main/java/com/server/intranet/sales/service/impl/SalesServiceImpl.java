package com.server.intranet.sales.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.server.intranet.sales.entity.SalesEntity;
import com.server.intranet.sales.repository.SalesRepository;
import com.server.intranet.sales.service.SalesService;

import reactor.core.publisher.Mono;

@Service
public class SalesServiceImpl implements SalesService {
	private final SalesRepository salesRepository;
	
	public SalesServiceImpl(SalesRepository salesRepository) {
		this.salesRepository = salesRepository;
	}

	@Override
	public SalesEntity saveSalesData(SalesEntity sales) {
		return salesRepository.save(sales);
	}

	
}
