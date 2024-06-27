package com.server.intranet.login.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Table(name = "AUTHORITY")
@AllArgsConstructor
@NoArgsConstructor
public class AuthorityEntity {

    @Id
    @Column(name = "AUTHORITY_CODE")
    private Long AUTHORITY_CODE;

    @Column(name = "AUTHORITY_NAME", nullable = false)
    private String AUTHORITY_NAME;

}
