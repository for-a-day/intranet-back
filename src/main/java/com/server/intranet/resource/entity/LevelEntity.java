package com.server.intranet.resource.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Data
@Table(name = "LEVEL")
@AllArgsConstructor
@NoArgsConstructor
public class LevelEntity {

    @Id
    @Column(name = "LEVEL_CODE", nullable = false, unique = true)
    private String levelCode;

    @Column(name = "LEVEL_NAME", nullable = false)
    private String levelName;
}