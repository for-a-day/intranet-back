package com.server.intranet.resource.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePasswordDTO {
    private Long employeeId;
    private String currentPassword;
    private String newPassword;
}
