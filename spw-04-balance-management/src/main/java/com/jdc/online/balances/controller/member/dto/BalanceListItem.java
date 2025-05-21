package com.jdc.online.balances.controller.member.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.jdc.online.balances.model.entity.Ledger;
import com.jdc.online.balances.model.entity.LedgerEntry;
import com.jdc.online.balances.model.entity.LedgerEntry_;
import com.jdc.online.balances.model.entity.consts.BalanceType;
import com.jdc.online.balances.model.entity.embeddables.LedgerEntryPk;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public record BalanceListItem(
		String code,
		LocalDateTime issuedAt,
		BalanceType type,
		String ledger,
		String particular,
		BigDecimal expenses,
		BigDecimal incomes,
		BigDecimal balance
		) {

	public BalanceListItem(
			LedgerEntryPk id, 
			Ledger entity,
			LocalDateTime issuedAt,
			String particular,
			BigDecimal amount,
			BigDecimal lastAmount) {
		
		this(
			id.getCode(),
			issuedAt,
			entity.getType(),
			entity.getName(),
			particular,
			entity.getType() == BalanceType.Expenses ? amount : BigDecimal.ZERO,
			entity.getType() == BalanceType.Incomes ? amount : BigDecimal.ZERO,
			entity.getType() == BalanceType.Incomes ? lastAmount.add(amount) : lastAmount.subtract(amount));
	}
	
	public static void select(CriteriaQuery<BalanceListItem> cq, Root<LedgerEntry> root) {
		cq.multiselect(
				root.get(LedgerEntry_.id),
				root.get(LedgerEntry_.ledger),
				root.get(LedgerEntry_.issuedAt),
				root.get(LedgerEntry_.particular),
				root.get(LedgerEntry_.amount),
				root.get(LedgerEntry_.lastAmount));
	}

}
