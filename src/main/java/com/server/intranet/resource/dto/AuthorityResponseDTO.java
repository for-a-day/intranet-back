package com.server.intranet.resource.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorityResponseDTO {

    private Long authorityCode;

    private String authorityName;

}
