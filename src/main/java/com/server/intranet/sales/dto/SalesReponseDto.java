package com.server.intranet.sales.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesReponseDto {
	
	private Long salesId;
	private int monthlySales; 
	private int year;
	private int month; 
	
}
