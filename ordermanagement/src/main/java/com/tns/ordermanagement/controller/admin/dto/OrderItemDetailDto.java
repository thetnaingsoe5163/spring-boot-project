package com.tns.ordermanagement.controller.admin.dto;

import com.tns.ordermanagement.model.entity.SaleItem;

public record OrderItemDetailDto(
		int itemId,
		String englishName,
		String burmeseName,
		String category,
		String details,
		int salePrice,
		int lastQuantity,
		int previousQuantity,
		boolean modified,
		String reason
		) {

	public OrderItemDetailDto(SaleItem entity) {
		this(
				entity.getItem().getId(),
				entity.getItem().getEnglishName(),
				entity.getItem().getBurmeseName(),
				entity.getItem().getCategory().getName(),
				entity.getDetails(),
				entity.getSalePrice(),
				entity.getLastQuantity(),
				entity.getPreviousQuantity(),
				entity.isModified(),
				entity.getReason()
				);
	}
}
