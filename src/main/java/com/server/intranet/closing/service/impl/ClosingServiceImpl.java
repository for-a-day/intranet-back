package com.server.intranet.closing.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.intranet.closing.dto.ClosingRequestDto;
import com.server.intranet.closing.dto.ClosingResponseDto;
import com.server.intranet.closing.entity.ClosingEntity;
import com.server.intranet.closing.repository.ClosingRepository;
import com.server.intranet.closing.service.ClosingService;

@Service
public class ClosingServiceImpl implements ClosingService{
	private final ClosingRepository closingRepository;
	
	@Autowired
	public ClosingServiceImpl(ClosingRepository closingRepository) {
		this.closingRepository = closingRepository;
	}
	
	// 목록
	@Override
	public List<ClosingResponseDto> closeList() {
		return closingRepository.findAll().stream()
				.map(close -> new ClosingResponseDto(
						close.getClosingId(), 
						close.getClosingName(), 
						close.getOwner(),
						close.getAddress(), 
						close.getPhoneNumber(), 
						close.getContractDate(), 
						close.getExpirationDate(), 
						close.getWarningCount(), 
						close.getClosingDate(), 
						close.getEmployeeId(),
						close.getClosingReason()
					    )) 
						.collect(Collectors.toList());
	}

	
	// 등록
	@Override
	public ClosingEntity insert(ClosingRequestDto dto) throws Exception {
		try {
			ClosingEntity closing = new ClosingEntity();
			closing.setClosingId(dto.getClosingId());
			closing.setClosingName(dto.getClosingName());
			closing.setOwner(dto.getOwner());		
			closing.setAddress(dto.getAddress());
			closing.setPhoneNumber(dto.getPhoneNumber());
			closing.setContractDate(dto.getContractDate());
			closing.setExpirationDate(dto.getExpirationDate());
			closing.setWarningCount(dto.getWarningCount());
			closing.setClosingDate(dto.getClosingDate());
			closing.setClosingReason(dto.getClosingReason());
			
			return closingRepository.save(closing);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("폐점 등록 중 에러 발생");
		}
	}
	
}
