package com.server.intranet.franchisee.service;

import java.util.List;

import com.server.intranet.franchisee.dto.FranchiseeRequestDto;
import com.server.intranet.franchisee.dto.FranchiseeResponsetDto;
import com.server.intranet.franchisee.entity.FranchiseeEntity;

public interface FranchiseeService {
	List<FranchiseeResponsetDto> franList(); //목록
	FranchiseeEntity findId(String franshiseeId); //아이디 찾기
	FranchiseeEntity edit(FranchiseeRequestDto dto) throws Exception; //수정
	FranchiseeEntity delete(String franchiseeId);//삭제
	FranchiseeEntity insert(FranchiseeRequestDto dto) throws Exception; //입력
}
