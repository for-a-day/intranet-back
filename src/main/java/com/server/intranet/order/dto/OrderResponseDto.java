package com.server.intranet.order.dto;

import java.sql.Date;

import com.server.intranet.franchisee.entity.FranchiseeEntity;
import com.server.intranet.menu.entity.MenuEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {
	private Long order_id;  // 주문 아이디
	private int order_quantity; // 주문 수량
	private int order_price; // 총액
	private Date order_date; // 주문 날짜   
	private String franchisee_id; // 가맹점 아이디
	private String menu_id;  // 메뉴 아이디 
}
