package com.server.intranet.franchisee.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.server.intranet.franchisee.dto.FranchiseeResponsetDto;
import com.server.intranet.franchisee.repository.FranchiseeRepository;
import com.server.intranet.franchisee.service.FranchiseeService;

@Service
public class FranchiseeServiceImpl implements FranchiseeService{
	
	private final FranchiseeRepository franchiseeRepository;
	
	private FranchiseeServiceImpl(FranchiseeRepository franchiseeRepository) {
		this.franchiseeRepository = franchiseeRepository;
	}

	@Override
    public List<FranchiseeResponsetDto> franList() {
        return franchiseeRepository.findAll().stream()
                .map(fran -> new FranchiseeResponsetDto(
                        fran.getFranchiseeId(),
                        fran.getEmployee_id(), // EmployeeEntity 객체
                        fran.getEmployee_id().getNAME(), // 사원 이름
                        fran.getFranchiseeName(),
                        fran.getOwner(),
                        fran.getAddress(),
                        fran.getPhoneNumber(),
                        fran.getContractDate(),
                        fran.getExpirationDate(),
                        fran.getWarningCount()
                ))
                .collect(Collectors.toList());
    }
	
	
}
