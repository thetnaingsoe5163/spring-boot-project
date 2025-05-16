package com.tns.ordermanagement.controller.guest.dto;

import java.util.List;

import lombok.Data;

@Data
public class OrderForm {

	private Integer formId;
	
	private List<OrderItem> items;
}
