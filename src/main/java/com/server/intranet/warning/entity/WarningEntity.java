package com.server.intranet.warning.entity;

import com.server.intranet.closing.entity.ClosingEntity;
import com.server.intranet.franchisee.entity.FranchiseeEntity;

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
import lombok.ToString;

@Entity
@Getter
@Setter
@Builder
@Table(name = "warning")
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class WarningEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "warning_id")
    private Long warningId;
	
	@Column(name = "warning_reason", length = 255)
    private String warningReason;
	
	@ManyToOne
	@JoinColumn(name = "franchisee_id", nullable = true)
	private FranchiseeEntity franchiseeId;
	
	@ManyToOne
	@JoinColumn(name = "closing_id", nullable = true)
	private ClosingEntity closing_id;
}
