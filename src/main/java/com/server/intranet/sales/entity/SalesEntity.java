package com.server.intranet.sales.entity;

import com.server.intranet.franchisee.entity.FranchiseeEntity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Builder
@Table(name = "sales_management")
@ToString
public class SalesEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sales_id")  // 매출 아이디
	private Long salesId;
	
	@Column(name = "monthly_sales", nullable = false)
	private int monthlySales;   // 월별 매출액
	
	@Column(name = "year", nullable = false)
	private int year;    // 년도
	
	@Column(name = "month", nullable = false)
	private int month;  // 월
	
	@ManyToOne
	@JoinColumn(name = "franchisee_id", nullable = false)  //가맹점 아이디
	private FranchiseeEntity franchiseeId; 
	
	
}
