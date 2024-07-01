package com.server.intranet.warning.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.intranet.franchisee.entity.FranchiseeEntity;
import com.server.intranet.warning.dto.WarningRequestDto;
import com.server.intranet.warning.dto.WarningResponseDto;
import com.server.intranet.warning.entity.WarningEntity;
import com.server.intranet.warning.repository.WarningRepository;
import com.server.intranet.warning.service.WarningService;

@Service
public class WarningServiceImpl implements WarningService {
	private final WarningRepository warningRepository;
	
	@Autowired
	public WarningServiceImpl(WarningRepository warningRepository) {
		this.warningRepository = warningRepository;
	}
	// 목록
	@Override
	public List<WarningResponseDto> warnList() {
		return warningRepository.findAll().stream()
				.map(warn -> {
					String franName = warn.getFranchiseeId() != null ? warn.getFranchiseeId().getFranchiseeName() : " ";
					String closingName = warn.getClosing_id() != null ? warn.getClosing_id().getClosingName() : " ";
					return new WarningResponseDto(
							warn.getWarningId(), 
							warn.getWarningReason(), 
							warn.getFranchiseeId(), 
							warn.getClosing_id(), 
							franName, 
							closingName);
				})
				.collect(Collectors.toList());
	}
	
	// 등록
	@Override
	public WarningEntity insert(WarningRequestDto dto) throws Exception {
		WarningEntity warning = new WarningEntity();
		//warning.setWarningId(dto.getWarningId());
		warning.setFranchiseeId(dto.getFranchisee_id());
		warning.setWarningReason(dto.getWarningReason());
		warning.setClosing_id(dto.getClosing_id());
		return warningRepository.save(warning);
	}
	
	// 수정
	@Override
	public WarningEntity update(FranchiseeEntity franchisee_id, WarningRequestDto dto) throws Exception {
		try {
			
			WarningEntity warning = warningRepository.findByFranchiseeId(franchisee_id)
					.orElseThrow(() -> new RuntimeException("해당 franchisee_id에 해당하는 경고가 없습니다."));
					warning.setFranchiseeId(dto.getFranchisee_id());
					warning.setWarningReason(dto.getWarningReason());
					warning.setClosing_id(dto.getClosing_id());
			
			return warningRepository.save(warning);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("폐점 경고 수정 중 에러 발생");
		}
	}

	// 아이디 찾기
	@Override
	public boolean existId(FranchiseeEntity franchisee_id) {
		Optional<WarningEntity> franExist = warningRepository.findByFranchiseeId(franchisee_id);
		
		return franExist.isPresent();
	}

}
