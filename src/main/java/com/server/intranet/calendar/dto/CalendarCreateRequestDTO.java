package com.server.intranet.calendar.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class CalendarCreateRequestDTO {
	
	@NonNull
	private String calendarName;
	
	private Long departmentCode;
}
