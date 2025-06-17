package com.tns.ordermanagement.controller.commondto;

import java.math.BigDecimal;

import com.tns.ordermanagement.model.entity.SaleItem;

public record ReceiptItem(
		String englishName,
		String burmeseName,
		String details,
		int quantity,
		int salePrice
		) {

	public static ReceiptItem convert(SaleItem i) {
		return new ReceiptItem(
				i.getItem().getEnglishName(), 
				i.getItem().getBurmeseName(), 
				i.getDetails(), 
				i.getPreviousQuantity(), 
				i.getSalePrice());
	}
	
	public BigDecimal totalPrice() {
		return BigDecimal.valueOf(quantity * salePrice);
	}

}
