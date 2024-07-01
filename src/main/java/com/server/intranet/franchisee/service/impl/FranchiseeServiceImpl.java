package com.server.intranet.franchisee.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.intranet.franchisee.dto.FranchiseeRequestDto;
import com.server.intranet.franchisee.dto.FranchiseeResponsetDto;
import com.server.intranet.franchisee.entity.FranchiseeEntity;
import com.server.intranet.franchisee.repository.FranchiseeRepository;
import com.server.intranet.franchisee.service.FranchiseeService;
import com.server.intranet.resource.entity.EmployeeEntity;
import com.server.intranet.resource.repository.ResourceRepository;

@Service
public class FranchiseeServiceImpl implements FranchiseeService{
	
	private final FranchiseeRepository franchiseeRepository;
	private final ResourceRepository resourceRepository;
	
    @Autowired
    public FranchiseeServiceImpl(FranchiseeRepository franchiseeRepository, ResourceRepository resourceRepository) {
        this.franchiseeRepository = franchiseeRepository;
        this.resourceRepository = resourceRepository;
    }
	
	// 목록
	@Override
    public List<FranchiseeResponsetDto> franList() {
        return franchiseeRepository.findAll().stream()
                .map(fran -> new FranchiseeResponsetDto(
                        fran.getFranchiseeId(),
                        fran.getEmployee_id(), // EmployeeEntity 객체
                        fran.getEmployee_id().getName(), // 사원 이름
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

	
	// 아이디 찾기
	@Override
	public FranchiseeEntity findId(String franshiseeId) {
		Optional<FranchiseeEntity> optional = franchiseeRepository.findById(franshiseeId);
		return optional.orElse(null);
	}
	
	// 수정 기능
	@Override
	public FranchiseeEntity edit(FranchiseeRequestDto dto) throws Exception {
        // EmployeeEntity를 찾고, Long 타입으로 변환합니다.
        EmployeeEntity employee = resourceRepository.findById(Long.parseLong(dto.getEmployeeId()))
                .orElseThrow(() -> new Exception("Employee not found"));

        // FranchiseeEntity로 변환
        FranchiseeEntity franchisee = new FranchiseeEntity();
        franchisee.setFranchiseeId(dto.getFranchiseeId());
        franchisee.setEmployee_id(employee); // setEmployee_id로 수정
        franchisee.setFranchiseeName(dto.getFranchiseeName());
        franchisee.setOwner(dto.getOwner());
        franchisee.setAddress(dto.getAddress());
        franchisee.setPhoneNumber(dto.getPhoneNumber());
        franchisee.setContractDate(dto.getContractDate());
        franchisee.setExpirationDate(dto.getExpirationDate());
        franchisee.setWarningCount(dto.getWarningCount());

        // 업데이트
        return franchiseeRepository.save(franchisee);
    }
	
	// 삭제 기능
	@Override
	public void delete(String franchiseeId) {
	    franchiseeRepository.deleteById(franchiseeId);
	}
	
	// 등록
	@Override
	public FranchiseeEntity insert(FranchiseeRequestDto dto) throws Exception{
        // EmployeeEntity를 찾고, Long 타입으로 변환합니다.
        EmployeeEntity employee = resourceRepository.findById(Long.parseLong(dto.getEmployeeId()))
                .orElseThrow(() -> new Exception("Employee not found"));

        // FranchiseeEntity로 변환
        FranchiseeEntity franchisee = new FranchiseeEntity();
        franchisee.setFranchiseeId(dto.getFranchiseeId());
        franchisee.setEmployee_id(employee); // setEmployee_id로 수정
        franchisee.setFranchiseeName(dto.getFranchiseeName());
        franchisee.setOwner(dto.getOwner());
        franchisee.setAddress(dto.getAddress());
        franchisee.setPhoneNumber(dto.getPhoneNumber());
        franchisee.setContractDate(dto.getContractDate());
        franchisee.setExpirationDate(dto.getExpirationDate());
        franchisee.setWarningCount(dto.getWarningCount());

        // 업데이트
        return franchiseeRepository.save(franchisee);
	}

}
