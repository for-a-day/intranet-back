package com.server.intranet.order.entity;

import java.sql.Date;

import com.server.intranet.franchisee.entity.FranchiseeEntity;
import com.server.intranet.menu.entity.MenuEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Builder
@Table(name = "order_management")
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity { // order가 many, menu가 one
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(length = 50)
	private Long order_id;                 // 주문 아이디
	
	@Column(length = 100, nullable = false)  // 주문 수량
	private int order_quantity;
	
	@Column(length = 100, nullable = false)  // 총액
	private int order_price;
	
	@Column
	private Date order_date;                 // 주문 날짜
	
	@ManyToOne
	@JoinColumn(name = "franchisee_id", nullable = false)   // 가맹점 아이디
	private FranchiseeEntity franchisee_id;
	
	@ManyToOne
	@JoinColumn(name = "menu_id", nullable = false) // 메뉴 아이디 
	private MenuEntity menu_id;
		
}
