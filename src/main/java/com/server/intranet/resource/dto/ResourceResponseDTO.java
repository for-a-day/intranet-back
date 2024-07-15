package com.server.intranet.resource.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ResourceResponseDTO {
    private Long employeeId;
    private String name;
    private String gender;
    private Date birth;
    private Date dateEmployment;
    private String contact;
    private String address;
    private String emailAddress;
    private String employmentStatus;
    private String level;
    private String department;
    private String authority;
}
