package com.tns.ordermanagement.controller.admin.dto;

import java.util.UUID;

import com.tns.ordermanagement.model.entity.OrderTransaction;
import com.tns.ordermanagement.model.entity.constant.OrderTransactionStatus;

public record OrderTrxDto(
		UUID id,
		int tableNumber,
		OrderTransactionStatus status
		) {

	public OrderTrxDto(OrderTransaction entity) {
		this(entity.getId(), entity.getRestaurantTable().getTableNumber(),entity.getStatus());
	}
}
