package com.tns.ordermanagement.controller.admin.dto;

import java.util.UUID;

import com.tns.ordermanagement.model.entity.SaleItem;

public record SaleItemDto(
		UUID saleId,
		int itemId,
		String name,
		String category,
		String details,
		int salePrice,
		int quantity
		) {

	public SaleItemDto(SaleItem item) {
		this(	
				item.getPk().getTransaction(),
				item.getPk().getItemId(),
				item.getItem().getEnglishName(),
				item.getItem().getCategory().getName(),
				item.getDetails(),
				item.getSalePrice(),
				item.getLastQuantity());
	}
	
	public OrderSubmitItem convertToOrderItem() {
		var order = new OrderSubmitItem();
		order.setSaleId(this.saleId);
		order.setItemId(this.itemId);
		order.setName(this.name);
		order.setCategory(this.category);
		order.setDetails(this.details);
		order.setQuantity(this.quantity);
		order.setSalePrice(this.salePrice);
		order.setDeleted(false);
		return order;
	}
}
