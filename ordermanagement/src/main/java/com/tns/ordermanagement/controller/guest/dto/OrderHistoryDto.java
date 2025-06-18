package com.tns.ordermanagement.controller.guest.dto;

import java.math.BigDecimal;
import java.util.List;

import com.tns.ordermanagement.model.entity.OrderTransaction;
import com.tns.ordermanagement.model.entity.OrderTransaction_;
import com.tns.ordermanagement.model.entity.constant.OrderTransactionStatus;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public record OrderHistoryDto(
		OrderTransactionStatus status,
		List<OrderHistoryItemDto> items
		) {

	public BigDecimal getTotalAmount() {
		return items.stream().map(a -> BigDecimal.valueOf(a.getTotal())).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	public int getTotalQuantity() {
		return items.stream().map(a -> a.quantity()).reduce(0, Integer::sum);
	}

	public static void select(CriteriaBuilder cb, CriteriaQuery<OrderHistoryDto> cq, Root<OrderTransaction> root) {
		cq.multiselect(
				root.get(OrderTransaction_.customerSessionId),
				root.get(OrderTransaction_.status),
				root.get(OrderTransaction_.items)
				);
	}

	public static OrderHistoryDto convert(OrderTransaction order) {
		var o = new OrderHistoryDto(
				order.getStatus(),
				OrderHistoryItemDto.convert(order.getItems()));
		return o;
	}
}
