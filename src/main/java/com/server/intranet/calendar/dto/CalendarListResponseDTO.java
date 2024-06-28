package com.server.intranet.calendar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CalendarListResponseDTO {
	private Long calendarId;
	private String calendarName;
	private Long departmentCode;
}
