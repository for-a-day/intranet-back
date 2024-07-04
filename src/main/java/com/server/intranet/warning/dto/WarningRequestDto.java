package com.server.intranet.warning.dto;

import jakarta.annotation.Nullable; 
import lombok.Getter;
import lombok.NonNull; 

import com.server.intranet.closing.entity.ClosingEntity;
import com.server.intranet.franchisee.entity.FranchiseeEntity;

import lombok.Setter;

@Getter
@Setter
public class WarningRequestDto {
	 
	 //경고 아이디
	 //private Long warningId;
	
	 @NonNull //경고 사유
	 private String warningReason;
	 
	 @Nullable //가맹점 아이디
	 private String franchisee_id;
	 
	 @Nullable // 폐점 아이디
	 private String closing_id;

	 public WarningRequestDto( @NonNull String warningReason, String franchisee_id,
			 String closing_id) {
		super();
		this.warningReason = warningReason;
		this.franchisee_id = franchisee_id;
		this.closing_id = closing_id;
	 } 
}
