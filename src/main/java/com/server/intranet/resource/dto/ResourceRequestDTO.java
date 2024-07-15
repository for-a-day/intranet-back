package com.server.intranet.resource.dto;

import com.server.intranet.resource.entity.AuthorityEntity;
import com.server.intranet.resource.entity.DepartmentEntity;
import com.server.intranet.resource.entity.LevelEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;


@Getter
@Setter
@ToString
public class ResourceRequestDTO {

        private Long employeeId;
        private Long employeePassword;
        private String name;
        private String gender;
        private Date birth;
        private Date dateEmployment;
        private String contact;
        private String address;
        private String emailAddress;
        private String employmentStatus;
        private Long levelCode; // 직급 코드
        private Long departmentCode; // 부서 코드
        private Long authorityCode; // 권한 코드
}
