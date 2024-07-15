package com.server.intranet.menu.dto;

import lombok.Getter;

// 메뉴 리스트 보기, 메뉴 상세보기
@Getter
public class MenuResponseDto {
	// 메뉴 아이디
	private String menu_id; 
	// 메뉴 이름
	private String menu_name;
	// 메뉴 가격
	private int menu_price;
	// 메뉴 레시피
	private String menu_recipe; 
	// 메뉴 원가
	private int menu_origin_price;
	// 메뉴단종 여부
	private int menu_end;
	
	public MenuResponseDto(String menu_id, String menu_name, int menu_price, String menu_recipe, int menu_origin_price,
			int menu_end) {
		super();
		this.menu_id = menu_id;
		this.menu_name = menu_name;
		this.menu_price = menu_price;
		this.menu_recipe = menu_recipe;
		this.menu_origin_price = menu_origin_price;
		this.menu_end = menu_end;
	}
	
}
