package com.tns.ordermanagement.controller.guest.dto;

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
				item.getLastQuantity(),
				item.getDetails());
	}
	
	public int getTotal() {
		return price * quantity;
	}
}
