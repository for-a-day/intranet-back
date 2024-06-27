package com.server.intranet.menu.entity;

import java.util.Set;

import com.server.intranet.menu.entity.MenuEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Builder
@Table(name = "menu")
@ToString(callSuper = true)
public class MenuEntity {
	
	@Id
	@Column(length = 50)
	private String menu_id;                  // 메뉴 아이디
	
	@Column(length = 100, nullable = false)  // 메뉴이름
	private String menu_name;               
	
	@Column(length = 100, nullable = false)  // 메뉴가격
	private int menu_price;
	
	@Column(length = 50, nullable = false)   // 메뉴 레시피
	private String menu_recipe; 
	
	@Column(length = 100, nullable = false)  // 메뉴 원가
	private int memu_origin_price;
	
	@Column(length = 5, nullable = false)    // 메뉴단종 여부
	private int menu_end;
	
//	@OneToMany(mappedBy = "menu")
//	private Set<OrderEntity> orders;
}
