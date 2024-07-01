package com.server.intranet.menu.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name = "menu")
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class MenuEntity {
	
	// 메뉴 아이디
	@Id
	@Column(length = 50)
	private String menu_id;    
	
	// 메뉴이름
	@Column(length = 100, nullable = false)  
	private String menu_name;     
	
	// 메뉴가격
	@Column(length = 100, nullable = false)  
	private int menu_price;
	
	// 메뉴 레시피
	@Column(length = 50, nullable = false)   
	private String menu_recipe; 
	
	// 메뉴 원가 - DB 생성 다시 해요... memu => menu로 변경
	@Column(length = 100, nullable = false) 
	private int menu_origin_price;
	
	// 메뉴단종 여부
	@Column(length = 5, nullable = false)    
	private int menu_end;
	
//    public MenuEntity() {
//        this.menu_end = 1; // Default 1 (판매)
//    }
//    
//    public MenuEntity(String menu_id, String menu_name, int menu_price, String menu_recipe, int memu_origin_price, int menu_end) {
//        this.menu_id = menu_id;
//        this.menu_name = menu_name;
//        this.menu_price = menu_price;
//        this.menu_recipe = menu_recipe;
//        this.memu_origin_price = memu_origin_price;
//        this.menu_end = menu_end;
//    }
	
//	@OneToMany(mappedBy = "menu")
//	private Set<OrderEntity> orders;
}
