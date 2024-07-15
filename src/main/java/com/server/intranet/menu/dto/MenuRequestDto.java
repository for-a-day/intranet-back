package com.server.intranet.menu.dto;

import io.micrometer.common.lang.NonNull;
import lombok.Getter;
import lombok.Setter;

// 메뉴 등록, 메뉴 삭제, 메뉴 수정
@Getter
@Setter
public class MenuRequestDto {
	@NonNull // 메뉴 아이디
	private String menu_id; 
	@NonNull // 메뉴 이름
	private String menu_name;
	@NonNull // 메뉴 가격
	private int menu_price;
	@NonNull // 메뉴 레시피
	private String menu_recipe; 
	@NonNull // 메뉴 원가
	private int menu_origin_price;
	@NonNull // 메뉴단종 여부
	private int menu_end;	
}
