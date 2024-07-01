package com.server.intranet.closing.dto;

import java.sql.Date;

import com.server.intranet.resource.entity.EmployeeEntity;

import lombok.Getter;
import lombok.NonNull;

//목록, 상세보기 불러올때 사용
@Getter
public class ClosingResponseDto {
	
    @NonNull	// 폐점 아이디
	private String closingId;
    @NonNull    // 폐점 가게명
    private String closingName;
    @NonNull    // 주인
    private String owner;
    // 폐점 주소
    private String address;
    // 전화번호
    private String phoneNumber;
    // 계약일
    private Date contractDate;
    // 만료일
    private Date expirationDate;
    // 경고 횟수
    private Integer warningCount;
    // 폐점일자
    private Date closingDate;
    // 담당자ID
    private Long employeeId;
    // 폐점 사유
    private String closingReason;
    
	public ClosingResponseDto(@NonNull String closingId, @NonNull String closingName, @NonNull String owner,
			String address, String phoneNumber, Date contractDate, Date expirationDate, Integer warningCount,
			Date closingDate, Long employeeId, String closingReason) {
		super();
		this.closingId = closingId;
		this.closingName = closingName;
		this.owner = owner;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.contractDate = contractDate;
		this.expirationDate = expirationDate;
		this.warningCount = warningCount;
		this.closingDate = closingDate;
		this.employeeId = employeeId;
		this.closingReason = closingReason;
	}
    
    
}
