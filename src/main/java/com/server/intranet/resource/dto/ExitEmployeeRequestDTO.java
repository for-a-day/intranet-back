package com.server.intranet.resource.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class ExitEmployeeRequestDTO {

    private Long employeeId;

    private String name;

    private String gender;

    private Date birth;

    private Date dateEmployment;

    private String contact;

    private String emailAddress;

    private String levelName;

    private String departmentName;

    private Date dateRetirement;

    private String reasonRetirement;
}
