package com.server.intranet.login.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Table(name = "DEPARTMENT")
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentEntity {

    @Id
    @Column(name = "DEPARTMENT_CODE")
    private Long DEPARTMENT_CODE;

    @Column(name = "DEPARTMENT_NAME", nullable = false)
    private String DEPARTMENT_NAME;
}
