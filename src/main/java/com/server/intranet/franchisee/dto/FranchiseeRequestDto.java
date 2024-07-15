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
    private String franchiseeId;
    private String franchiseeName;
    private String employeeId;
    private String owner;
    private String address;
    private String phoneNumber;
    private Date contractDate;
    private Date expirationDate;
    private int warningCount;
}
