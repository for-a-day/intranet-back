package com.server.intranet.closing.dto;

import java.sql.Date;

import io.micrometer.common.lang.Nullable;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

//등록, 삭제, 수정 등 DB만 바뀔때
@Setter
@Getter
public class ClosingRequestDto {
	
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
    @Nullable// 담당자명
    private Long employeeId;
    // 폐점 사유
    private String closingReason;
	
}
