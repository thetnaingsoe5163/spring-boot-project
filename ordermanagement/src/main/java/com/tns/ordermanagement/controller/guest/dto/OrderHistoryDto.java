package com.tns.ordermanagement.controller.guest.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.tns.ordermanagement.model.entity.constant.Status;

public record OrderHistoryDto(
		UUID saleId,
		Status status,
		List<OrderHistoryItemDto> items
		) {

	public BigDecimal getTotalAmount() {
		return items.stream().map(a -> BigDecimal.valueOf(a.getTotal())).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	public int getTotalQuantity() {
		return items.stream().map(a -> a.quantity()).reduce(0, Integer::sum);
	}
}
