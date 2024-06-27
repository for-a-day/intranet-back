package com.server.intranet.franchisee.entity;

import java.sql.Date;
import java.util.Set;

import com.server.intranet.resource.entity.EmployeeEntity;
import com.server.intranet.sales.entity.SalesEntity;
import com.server.intranet.warning.entity.WarningEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "franchisee")
@ToString
public class FranchiseeEntity {
	
	@Id
	@Column(name = "franchisee_id")  //가맹점 아이디
	private String franchiseeId;
	
	//사원아이디 - 참조 관계
	@ManyToOne
	@JoinColumn(name = "employee_id", nullable = false)
	private EmployeeEntity employee_id;
	
	@Column(name = "franchisee_name", nullable = false, length = 255) //가맹점명
	private String franchiseeName;
	
	@Column(name = "owner", nullable = false, length = 100)  //대표자명
	private String owner;
	
	@Column(name = "address", nullable = true , length = 255) //지점주소
	private String address;
	
	@Column(name = "phone_number", nullable = true, length = 100) // 연락처
	private String phoneNumber;
	
	@Column(name = "contract_date", nullable = false) //가맹계약일
	private Date contractDate;
	
	@Column(name = "expiration_date", nullable = false) // 만료계약일
	@Temporal(TemporalType.DATE)
	private Date expirationDate;
	
	@Column(name = "warning_count", nullable = true) //경고 횟수
	private int warningCount;
	
//	//cascade = CascadeType.ALL 은 고민을 해보자..
//	@OneToMany(mappedBy = "franchisee")
//	private Set<WarningEntity> warning;
//	
//	@OneToMany(mappedBy = "franchise") 
//	private Set<SalesEntity> sales;
}
