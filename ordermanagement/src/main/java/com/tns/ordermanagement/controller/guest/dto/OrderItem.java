package com.tns.ordermanagement.controller.guest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

	private Integer itemId;
	private Integer categoryId;
	private String englishName;
	private String burmeseName;
	private int unitPrice;
	private int quantity;
	private String details;
	private boolean deleted;
	
	public OrderItem clone() {
		return new OrderItem(
				this.itemId,
				this.categoryId,
				this.englishName,
				this.burmeseName,
				this.unitPrice,
				this.quantity,
				this.details,
				this.deleted
				);
	}
}
