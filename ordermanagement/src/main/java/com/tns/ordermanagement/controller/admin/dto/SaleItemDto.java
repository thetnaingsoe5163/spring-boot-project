package com.tns.ordermanagement.controller.admin.dto;

import com.tns.ordermanagement.model.entity.SaleItem;

public record SaleItemDto(
		String name,
		String category,
		int salePrice,
		int quantity
		) {

	public SaleItemDto(SaleItem item) {
		this(
				item.getItem().getEnglishName(),
				item.getItem().getCategory().getName(),
				item.getSalePrice(),
				item.getQuantity());
	}
}
