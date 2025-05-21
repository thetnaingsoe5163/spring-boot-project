package com.jdc.online.balances.controller.member.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.jdc.online.balances.model.entity.LedgerEntry;
import com.jdc.online.balances.model.entity.consts.BalanceType;

public record LedgerEntryDetails(
		String code,
		String ledgerName,
		BigDecimal amount,
		LocalDateTime issuedAt,
		String particular,
		List<LedgerEntryDetailsItem> items,
		BalanceType type
		) {

	public static LedgerEntryDetails from(LedgerEntry entity) {
		return new LedgerEntryDetails(
				entity.getId().getCode(), 
				entity.getLedger().getName(), 
				entity.getAmount(),
				entity.getIssuedAt(), 
				entity.getParticular(),
				entity.getItems().stream().map(LedgerEntryDetailsItem::from).toList(),
				entity.getLedger().getType());
	}
}
