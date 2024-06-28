package com.server.intranet.schedule.dto;

import java.sql.Time;
import java.util.Date;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class ScheduleCreateRequestDTO {

	private Long calendarId;
	
	@NonNull
	private String subject;
	
	private String content;
	private Date startDate;
	private Date endDate;
	private Time startTime;
	private Time endTime;
	private String location;
}
