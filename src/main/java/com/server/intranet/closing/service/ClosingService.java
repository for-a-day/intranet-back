package com.server.intranet.closing.service;

import java.util.List;

import com.server.intranet.closing.dto.ClosingRequestDto;
import com.server.intranet.closing.dto.ClosingResponseDto;
import com.server.intranet.closing.entity.ClosingEntity;

public interface ClosingService {
	List<ClosingResponseDto> closeList(); //목록
	ClosingEntity insert(ClosingRequestDto closingRequestDto) throws Exception;
}
