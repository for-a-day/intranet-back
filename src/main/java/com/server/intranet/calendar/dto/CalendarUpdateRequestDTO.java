package com.server.intranet.calendar.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class CalendarUpdateRequestDTO {
	
	@NonNull
	private String calendarName;
	
	private Long departmentCode;
}
