package com.tns.ordermanagement.controller.admin.dto;

import com.tns.ordermanagement.model.entity.RestaurantTable;
import com.tns.ordermanagement.model.entity.constant.TableStatus;

public record TableDto(
		int id,
		int tableNumber,
		TableStatus status
		) {

	public TableDto(RestaurantTable entity) {
		this(entity.getId(), entity.getTableNumber(), entity.getStatus());
	}
}

