package com.server.intranet.schedule.entity;

import java.util.Date;

import com.server.intranet.calendar.entity.CalendarEntity;
import com.server.intranet.login.entity.DepartmentEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Table(name = "SCHEDULE")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScheduleEntity {
	
	// 스케줄 아이디
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SCHEDULE_ID")
	private Long SCHEDULE_ID;
	
	// 캘린더 아이디
	@ManyToOne
	@JoinColumn(name = "CALENDAR_ID")
	private CalendarEntity calendar;
	
	// 제목
	@Column(name = "SUBJECT" , nullable = false)
	private String SUBJECT;
	
	// 내용
	@Column(name = "CONTENT")
	private String CONTENT;
	
	// 시작일
	@Column(name = "START_DATE")
	private Date START_DATE;
	
	// 종료일
	@Column(name = "END_DATE")
	private Date END_DATE;
	
	// 장소
	@Column(name = "LOCATION")
	private String LOCATION;
	
}
