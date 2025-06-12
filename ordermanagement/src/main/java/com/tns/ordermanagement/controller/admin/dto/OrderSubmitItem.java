package com.tns.ordermanagement.controller.admin.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class OrderSubmitItem {
	
	private UUID saleId;
	private int itemId;
	private String name;
	private String category;
	private String details;
	private int salePrice;
	private int quantity;
	private boolean deleted;
	private boolean modified;
	private String reason;
}
