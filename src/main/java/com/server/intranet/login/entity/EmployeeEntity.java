package com.server.intranet.login.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Builder
@Table(name = "EMPLOYEE")
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeEntity {

    //아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMPLOYEE_ID")
    private Long EMPLOYEE_ID;

    //패스워드
    @Column(name = "EMPLOYEE_PASSWORD", nullable = false)
    private String EMPLOYEE_PASSWORD;

    //이름
    @Column(name = "NAME", nullable = false)
    private String NAME;

    //성별
    @Column(name = "GENDER", nullable = false)
    private String GENDER;

    //생년월일
    @Column(name = "BIRTH", nullable = false)
    private Long BIRTH;

    //입사날짜
    @Column(name = "DATE_EMPLOYMENT", nullable = false)
    private Date DATE_EMPLOYMENT;

    //연락처
    @Column(name = "CONTACT", nullable = false)
    private String CONTACT;

    //주소
    @Column(name = "ADDRESS", nullable = false)
    private String ADDRESS;

    //이메일주소
    @Column(name = "EMAIL_ADDRESS", unique = true, nullable = false)
    private String EMAIL_ADDRESS;

    //재직여부
    @Column(name = "EMPLOYMENT_STATUS", nullable = false)
    private String EMPLOYMENT_STATUS;

    @ManyToOne
    @JoinColumn(name = "LEVEL_CODE")
    private LevelEntity level;

    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_CODE")
    private DepartmentEntity department;

    @ManyToOne
    @JoinColumn(name = "AUTHORITY_CODE")
    private AuthorityEntity authority;
}
