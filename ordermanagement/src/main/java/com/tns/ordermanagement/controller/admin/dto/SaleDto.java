package com.tns.ordermanagement.controller.admin.dto;

import java.util.UUID;

import com.tns.ordermanagement.model.entity.Sale;
import com.tns.ordermanagement.model.entity.constant.Status;

public record SaleDto(
		UUID id,
		Status status
		) {

	public SaleDto(Sale sale) {
		this(sale.getId(), sale.getStatus());
	}
}
