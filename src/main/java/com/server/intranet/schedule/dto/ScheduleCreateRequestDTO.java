package com.server.intranet.schedule.dto;

import java.sql.Time;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleCreateRequestDTO {

	private Long schduleId;
	private Long calendarId;
	
	@NonNull
	private String subject;
	private String content;
	private Date startDate;
	private Date endDate;
	private Time startTime;
	private Time endTime;
	private String location;
	private String writer;
}
