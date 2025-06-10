package com.tns.ordermanagement.controller.guest.dto;

import lombok.Data;

@Data
public class OrderItem {

	private Integer itemId;
	private Integer categoryId;
	private String englishName;
	private String burmeseName;
	private int unitPrice;
	private int quantity;
	private String details;
	private boolean deleted;
}
