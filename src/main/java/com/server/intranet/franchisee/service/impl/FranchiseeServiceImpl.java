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

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
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
	
	
	// api 받을려고 이거까지 만듬 (매출 api)
	// 기존의 프랜차이즈 엔터티 업데이트 메서드
	@Override
    public FranchiseeEntity updateFranchisee(String franchiseeId, FranchiseeEntity updatedData) {
        // 기존의 프랜차이즈 엔터티 조회
        FranchiseeEntity existingFranchisee = franchiseeRepository.findByFranchiseeId(franchiseeId);

        if (existingFranchisee != null) {
            // 엔터티 필드 업데이트 (기존 값을 유지한 채로 API 데이터로 업데이트)
            existingFranchisee.setFranchiseeName(updatedData.getFranchiseeName());
            existingFranchisee.setOwner(updatedData.getOwner());
            existingFranchisee.setAddress(updatedData.getAddress());
            existingFranchisee.setPhoneNumber(updatedData.getPhoneNumber());
            existingFranchisee.setContractDate(updatedData.getContractDate());
            existingFranchisee.setExpirationDate(updatedData.getExpirationDate());
            existingFranchisee.setWarningCount(updatedData.getWarningCount());

            // 업데이트된 엔터티 저장 및 반환
            return franchiseeRepository.save(existingFranchisee);
        } else {
        	throw new EntityNotFoundException("Franchisee not found for id: " + franchiseeId);
        }
    }
}
