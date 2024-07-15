package com.server.intranet.approval.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * packageName    : com.server.intranet.approval.entity
 * fileName       : Storage
 * author         : gladious
 * date           : 2024-06-28
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-28        gladious       최초 생성
 */
@Entity(name = "STORAGE")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Storage {

    //저장소 아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STORAGE_ID")
    private Long storageId;

    //저장소 명
    @Column(name = "NAME", nullable = false)
    private String name;

    //상위 저장소 아이디
    @Column(name = "PARENT_STORAGE")
    private Long pStorageId;

    //저장 위치
    @Column(name = "LOCATION")
    private String location;

    //저장소 깊이
    @Column(name = "DEPT")
    private String dept;
}
