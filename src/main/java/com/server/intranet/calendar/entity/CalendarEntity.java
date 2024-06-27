package com.server.intranet.calendar.entity;

import java.util.Date;

import com.server.intranet.resource.entity.DepartmentEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Table(name = "CALENDAR")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CalendarEntity {
	
	// 캘린더 아이디
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CALENDAR_ID")
	private Long CALENDAR_ID;
	
	// 캘린더 이름
	@Column(name = "CALENDAR_NAME", nullable = false)
	private String CALENDAR_NAME;
	
	// 생성날짜
	@Column(name = "CREATION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date CREATION_DATE;
	
	// 부서코드
	@ManyToOne
	@JoinColumn(name = "DEPARTMENT_CODE")
	private DepartmentEntity department;
	
	
	// 현재 시간 저장
    @PrePersist
    protected void onCreate() {
    	CREATION_DATE = new Date();
    }
}
