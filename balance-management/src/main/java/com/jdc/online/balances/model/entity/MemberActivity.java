package com.jdc.online.balances.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.jdc.online.balances.model.entity.consts.MemberStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class MemberActivity extends AbstractEntity {
	
	@Id
	private long id;
	
	@MapsId
	@OneToOne(optional = false)
	private Member member;

	private MemberStatus status;

	private BigDecimal balance;

	private LocalDateTime registeredAt;

	private LocalDateTime lastAccessedAt;

	private String reasonForStatusChange;

}