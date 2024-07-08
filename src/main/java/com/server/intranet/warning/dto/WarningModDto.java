package com.server.intranet.warning.dto;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WarningModDto {
	 @Nullable //가맹점 아이디
	 private String franchisee_id;
	 
	 @Nullable // 폐점 아이디
	 private String closing_id;

	public WarningModDto(String franchisee_id, String closing_id) {
		super();
		this.franchisee_id = franchisee_id;
		this.closing_id = closing_id;
	}
	 
}