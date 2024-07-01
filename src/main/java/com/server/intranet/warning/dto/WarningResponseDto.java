package com.server.intranet.warning.dto;

import com.server.intranet.closing.entity.ClosingEntity;
import com.server.intranet.franchisee.entity.FranchiseeEntity;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class WarningResponseDto {
	     
		 //경고 아이디
		 private Long warningId;
		 
		 @NonNull //경고 사유
		 private String warningReason;
		 
		 //가맹점 아이디
		 private FranchiseeEntity franchisee_id;
		 
		 // 가맹점 이름 
		 private String franchisee_name;
		 
		 // 폐점 아이디
		 private ClosingEntity closing_id;
		 
		 // 폐점 이름 
		 private String closing_name;		 
		 
		 public WarningResponseDto(Long warningId, @NonNull String warningReason, FranchiseeEntity franchisee_id,
				ClosingEntity closing_id, String franchisee_name, String closing_name) {
			super();
			this.warningId = warningId;
			this.warningReason = warningReason;
			this.franchisee_id = franchisee_id;
			this.closing_id = closing_id;
			this.franchisee_name = franchisee_name;
			this.closing_name = closing_name;
		 }
}
