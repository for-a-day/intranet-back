package com.server.intranet.warning.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.intranet.closing.entity.ClosingEntity;
import com.server.intranet.closing.repository.ClosingRepository;
import com.server.intranet.franchisee.entity.FranchiseeEntity;
import com.server.intranet.franchisee.repository.FranchiseeRepository;
import com.server.intranet.warning.dto.WarningModDto;
import com.server.intranet.warning.dto.WarningRequestDto;
import com.server.intranet.warning.dto.WarningResponseDto;
import com.server.intranet.warning.entity.WarningEntity;
import com.server.intranet.warning.repository.WarningRepository;
import com.server.intranet.warning.service.WarningService;

import jakarta.transaction.Transactional;

@Service
public class WarningServiceImpl implements WarningService {
	private final WarningRepository warningRepository;
	private final FranchiseeRepository franchiseeRepository;
	private final ClosingRepository closingRepository;
	
	@Autowired
	public WarningServiceImpl(WarningRepository warningRepository,ClosingRepository closingRepository, FranchiseeRepository franchiseeRepository) {
		this.warningRepository = warningRepository;
		this.closingRepository = closingRepository;
		this.franchiseeRepository = franchiseeRepository;
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
		
		FranchiseeEntity franchisee = franchiseeRepository.findByFranchiseeId(dto.getFranchisee_id());
		ClosingEntity closing = closingRepository.findByClosingId(dto.getClosing_id());
		
		warning.setFranchiseeId(franchisee);
		warning.setWarningReason(dto.getWarningReason());
		warning.setClosing_id(closing);
		
		return warningRepository.save(warning);
	}
	
	// 수정
	@Override
	public List<WarningEntity> update(String franchisee_id, WarningModDto dto) throws Exception {
		System.out.println("update 서비스 단 도착");
		try {
			System.err.println("update 서비스 try 문으로 입성");
			
			ClosingEntity closing = closingRepository.findByClosingId(dto.getClosing_id());
			
			String close_id = dto.getClosing_id();
			String fran_id = dto.getFranchisee_id();
			
			if(closing == null) {
				throw new RuntimeException("update service : 관련 폐점 ID가 null이래");
			}
			
			System.out.println("if문 통과");
			
			// 아이디 조회해서 넣어줄 객체 생성 => null이라 값도 null
			FranchiseeEntity franchisee = franchiseeRepository.findByFranchiseeId(dto.getFranchisee_id());
			FranchiseeEntity franchisee2 = franchiseeRepository.findByFranchiseeId(dto.getClosing_id());
			
			System.out.println("update 서비스 : franchisee => " + franchisee);
			System.err.println("update 서비스 : franchisee2 => " + franchisee2);
			System.out.println("update 서비스 : closing => " + closing);
			
			// 아이디 조회 후, 객체 주입 => null이라 값도 null
			List<WarningEntity> warning = warningRepository.findAllByFranchiseeId(franchisee2);
//					.orElseThrow(() -> new RuntimeException("해당 franchisee_id에 해당하는 경고가 없습니다."));			

			if (warning.isEmpty()) {
			    throw new RuntimeException("해당 franchisee_id에 해당하는 경고가 없습니다.");
			 }					

			System.err.println("update 서비스 : warning => " + warning); 
			
			for (WarningEntity warnings : warning) {
					warnings.setFranchiseeId(franchisee);
					warnings.setClosing_id(closing);
			 }
			
			System.err.println("update 서비스 : for문 통과"); 
			
			List<WarningEntity> updatedWarnings = warningRepository.saveAll(warning);
			
			System.err.println("update 서비스 : updatedWarnings" + updatedWarnings); 
			
			System.err.println("update 서비스 정상 구동 완료");
			return updatedWarnings;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("폐점 경고 수정 중 에러 발생");
		}
	}

	// 아이디 찾기
	@Override
	public boolean existId(String franchisee) {
		System.err.println(" existId 서비스 도착 ");

		boolean franExist = warningRepository.existsByFranchiseeId(franchisee);		
		
		System.err.println(" franExist의 결과는? " + franExist);
		
		return franExist;
	}

}