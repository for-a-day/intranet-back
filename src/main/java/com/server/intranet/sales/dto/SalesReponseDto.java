package com.server.intranet.sales.dto;

import com.server.intranet.franchisee.entity.FranchiseeEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SalesReponseDto {
	
	private Long salesId;
	private int monthlySales; 
	private int year;
	private int month; 
	private FranchiseeEntity franchiseeId;
	
	public SalesReponseDto(Long salesId, int monthlySales, int year, int month, FranchiseeEntity franchiseeId) {
		super();
		this.salesId = salesId;
		this.monthlySales = monthlySales;
		this.year = year;
		this.month = month;
		this.franchiseeId = franchiseeId;
	}
	
}
