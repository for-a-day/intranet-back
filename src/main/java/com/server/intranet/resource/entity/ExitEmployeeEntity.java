package com.server.intranet.resource.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@Table(name = "EXIT_EMPLOYEE")
@AllArgsConstructor
@NoArgsConstructor
public class ExitEmployeeEntity {

    //아이디
    @Id
    @Column(name = "EMPLOYEE_ID")
    private Long employeeId;

    //이름
    @Column(name = "NAME", nullable = false)
    private String name;

    //성별
    @Column(name = "GENDER", nullable = false)
    private String gender;

    //생년월일
    @Column(name = "BIRTH", nullable = false)
    private Date birth;

    //입사날짜
    @Column(name = "DATE_EMPLOYMENT", nullable = false)
    private Date dateEmployment;

    //연락처
    @Column(name = "CONTACT", nullable = false)
    private String contact;

    //이메일주소
    @Column(name = "EMAIL_ADDRESS", unique = true, nullable = false)
    private String emailAddress;

    @Column(name = "LEVEL_NAME", nullable = false)
    private String levelName;

    @Column(name = "DEPARTMENT_NAME", nullable = false)
    private String departmentName;

    @Column(name = "DATE_RETIREMENT" , nullable = false)
    private Date dateRetirement;

    @Column(name = "REASON_RETIREMENT", nullable = false)
    private String reasonRetirement;

}
