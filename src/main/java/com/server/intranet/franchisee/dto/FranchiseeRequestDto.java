package com.server.intranet.franchisee.dto;

import java.sql.Date;

import com.server.intranet.resource.entity.EmployeeEntity;

import lombok.NonNull;
import lombok.Getter;
import lombok.Setter;

// 등록, 삭제, 수정 등 DB만 바뀔때
@Setter
@Getter
public class FranchiseeRequestDto {
	
	@NonNull //가맹점 아이디
	private String franchiseeId;
	
	@NonNull //사원아이디 - 참조 관계
	private EmployeeEntity employee_id;
	
	// private String emplo_id;
	
	@NonNull //가맹점명
	private String franchiseeName;
	
	@NonNull //대표자명
	private String owner;
	
	@NonNull //지점주소
	private String address;
	
	@NonNull // 연락처
	private String phoneNumber;
	
	@NonNull //가맹계약일
	private Date contractDate;
	
	@NonNull // 만료계약일
	private Date expirationDate;
	
	// 경고횟수
	private int warningCount;
	
}
