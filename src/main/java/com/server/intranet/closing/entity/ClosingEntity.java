package com.server.intranet.closing.entity;
import java.sql.Date;
import java.util.Set;

import com.server.intranet.warning.entity.WarningEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Builder
@Table(name = "exit_franchisee")
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ClosingEntity {
	
	@Id
	@Column(name = "closing_id", length = 255)  // 폐점 아이디
	private String closingId;
	
	@Column(name = "closing_name", nullable = false, length = 255) // 가맹점명
	private String closingName;
	
	@Column(name = "owner", nullable = false, length = 100) // 대표자명
	private String owner;
	
	@Column(name = "address", nullable = true , length = 255) // 지점주소
	private String address;
	
	@Column(name = "phone_number", nullable = true, length = 100) //연락처
	private String phoneNumber;
	
	@Column(name = "contract_date", nullable = false) // 가맹계약일
	private Date contractDate;
	
	@Column(name = "expiration_date", nullable = false) // 만료계약일
	@Temporal(TemporalType.DATE)
	private Date expirationDate;
	
	@Column(name = "warning_count", nullable = true) //경고횟수
	private Integer warningCount;
	
	@Column(name = "closing_date", nullable = false) // 폐점일
	@Temporal(TemporalType.DATE)
	private Date closingDate;
	
	@Column(name = "closing_reason", nullable = false, length = 100) //폐점사유
	private String closingReason;
	
	@Column(name = "employee_id", nullable = true) // 사원아이디
	private Long employeeId;

}
