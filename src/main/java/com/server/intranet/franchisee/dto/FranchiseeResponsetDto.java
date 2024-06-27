package com.server.intranet.franchisee.dto;

import java.sql.Date;

import com.server.intranet.resource.entity.EmployeeEntity; 

import lombok.Getter;
import lombok.NonNull;

//목록, 상세보기 불러올때 사용
@Getter
public class FranchiseeResponsetDto {
	
	//가맹점 아이디
	private String franchiseeId;
	
	//사원아이디 - 참조 관계
	private EmployeeEntity employeeId;	

    // 사원 이름
    private String employeeName;
	
	//가맹점명
	private String franchiseeName;
	
	//대표자명
	private String owner;
	
	//지점주소
	private String address;
	
	// 연락처
	private String phoneNumber;
	
	//가맹계약일
	private Date contractDate;
	
	// 만료계약일
	private Date expirationDate;
	
	// 경고횟수
	private int warningCount;

    public FranchiseeResponsetDto(@NonNull String franchiseeId, @NonNull EmployeeEntity employeeId, @NonNull String employeeName,
            @NonNull String franchiseeName, @NonNull String owner, @NonNull String address, @NonNull String phoneNumber,
            @NonNull Date contractDate, @NonNull Date expirationDate, int warningCount) {
        super();
        this.franchiseeId = franchiseeId;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.franchiseeName = franchiseeName;
        this.owner = owner;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.contractDate = contractDate;
        this.expirationDate = expirationDate;
        this.warningCount = warningCount;
    }
}
