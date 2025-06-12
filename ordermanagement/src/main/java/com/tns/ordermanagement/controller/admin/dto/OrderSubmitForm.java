package com.tns.ordermanagement.controller.admin.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderSubmitForm {

	@NotNull(message = "ID must not be null.")
	private UUID id;
	
	@NotNull(message = "Order Item must not be null.")
	private List<OrderSubmitItem> orderItems = new ArrayList<>(); 
}
