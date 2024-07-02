package com.server.intranet.warning.service;

import java.util.List;

import com.server.intranet.franchisee.entity.FranchiseeEntity;
import com.server.intranet.warning.dto.WarningRequestDto;
import com.server.intranet.warning.dto.WarningResponseDto;
import com.server.intranet.warning.entity.WarningEntity;

public interface WarningService {
	
	List<WarningResponseDto> warnList();
	WarningEntity insert(WarningRequestDto dto) throws Exception;
	WarningEntity update(FranchiseeEntity franchisee_id, WarningRequestDto dto) throws Exception;
	boolean existId(FranchiseeEntity franchisee_id);
	
}
