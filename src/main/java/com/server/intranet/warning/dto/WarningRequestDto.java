package com.server.intranet.warning.dto;

import com.server.intranet.closing.entity.ClosingEntity;
import com.server.intranet.franchisee.entity.FranchiseeEntity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter
@Setter
public class WarningRequestDto {
	 
	 @NonNull //경고 사유
	 private String warningReason;
	 
	 @Nullable //가맹점 아이디
	 private FranchiseeEntity franchisee_id;
	 
	 @Nullable // 폐점 아이디
	 private ClosingEntity closing_id;

	 public WarningRequestDto(@NonNull String warningReason, FranchiseeEntity franchisee_id,
			ClosingEntity closing_id) {
		super();
		this.warningReason = warningReason;
		this.franchisee_id = franchisee_id;
		this.closing_id = closing_id;
	 } 
}
