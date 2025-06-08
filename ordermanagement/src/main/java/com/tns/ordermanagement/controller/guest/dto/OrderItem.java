package com.tns.ordermanagement.controller.guest.dto;

import lombok.Data;

@Data
public class OrderItem {

	private Integer id;
	private String englishName;
	private String burmeseName;
	private int quantity;
	private String details;
	private boolean deleted;
}
