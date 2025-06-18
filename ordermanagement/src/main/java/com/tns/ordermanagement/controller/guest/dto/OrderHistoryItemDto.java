package com.tns.ordermanagement.controller.guest.dto;

import java.util.List;

import com.tns.ordermanagement.model.entity.SaleItem;

public record OrderHistoryItemDto(
		String engName,
		String burName,
		int price,
		int quantity,
		String details
		) {

	public OrderHistoryItemDto(SaleItem item) {
		this(
				item.getItem().getEnglishName(),
				item.getItem().getBurmeseName(),
				item.getSalePrice(),
				item.getPreviousQuantity(),
				item.getDetails());
	}
	
	public int getTotal() {
		return price * quantity;
	}

	public static List<OrderHistoryItemDto> convert(List<SaleItem> items) {
		return items.stream().map(OrderHistoryItemDto::new).toList();
	}
}
