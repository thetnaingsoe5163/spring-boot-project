package com.jdc.online.balances.controller.member.dto;

import java.math.BigDecimal;

import com.jdc.online.balances.model.entity.LedgerEntryItem;

public record LedgerEntryDetailsItem(
		String itemName,
		BigDecimal price,
		int quantity,
		BigDecimal total
		) {

	public static LedgerEntryDetailsItem from(LedgerEntryItem entity) {
		return new LedgerEntryDetailsItem(
				entity.getItem(),
				entity.getUnitPrice(),
				entity.getQuantity(),
				entity.getUnitPrice().multiply(BigDecimal.valueOf(entity.getQuantity())));
	}
}
