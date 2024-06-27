package com.server.intranet.resource.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Table(name = "LEVEL")
@AllArgsConstructor
@NoArgsConstructor
public class LevelEntity {

    @Id
    @Column(name = "LEVEL_CODE", nullable = false, unique = true)
    private String LEVEL_CODE;

    @Column(name = "LEVEL_NAME", nullable = false)
    private String LEVEL_NAME;
}