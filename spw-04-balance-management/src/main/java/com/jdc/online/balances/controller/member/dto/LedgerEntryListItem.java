package com.jdc.online.balances.controller.member.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.jdc.online.balances.model.entity.LedgerEntry;
import com.jdc.online.balances.model.entity.LedgerEntry_;
import com.jdc.online.balances.model.entity.Ledger_;
import com.jdc.online.balances.model.entity.embeddables.LedgerEntryPk;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public record LedgerEntryListItem(
		String code,
		LocalDateTime issuedAt,
		String ledgerName,
		String particular,
		BigDecimal amount
		) {
	
	public LedgerEntryListItem(
			LedgerEntryPk pk,
			LocalDateTime issuedAt,
			String ledgerName,
			String particular,
			BigDecimal amount) {
		
		this(pk.getCode(), issuedAt, ledgerName, particular, amount);
	}

	public static void select(CriteriaQuery<LedgerEntryListItem> cq, Root<LedgerEntry> root) {
		cq.multiselect(
				root.get(LedgerEntry_.id),
				root.get(LedgerEntry_.issuedAt),
				root.get(LedgerEntry_.ledger).get(Ledger_.name),
				root.get(LedgerEntry_.particular),
				root.get(LedgerEntry_.amount));
	}
	
	public static LedgerEntryListItem from(LedgerEntry entity) {
		var item = new LedgerEntryListItem(
				entity.getId().getCode(), entity.getIssuedAt(), 
				entity.getLedger().getName(), entity.getParticular(), 
				entity.getAmount());
		return item;
	}

}
