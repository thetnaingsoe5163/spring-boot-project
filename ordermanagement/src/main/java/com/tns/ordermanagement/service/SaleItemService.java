package com.tns.ordermanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.tns.ordermanagement.controller.guest.dto.OrderHistoryDto;
import com.tns.ordermanagement.controller.guest.dto.OrderHistoryItemDto;
import com.tns.ordermanagement.model.repo.SaleItemRepo;
import com.tns.ordermanagement.model.repo.SaleRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaleItemService {

	private final SaleRepo saleRepo;
	private final SaleItemRepo saleItemrepo;

	public List<OrderHistoryDto> findBySaleId(List<UUID> ids) {
		var orderList = new ArrayList<OrderHistoryDto>();
		
		for (var id : ids) {
			saleRepo.findById(id).ifPresent(s -> {
				var saleItemList = s.getSaleItems().stream().map(OrderHistoryItemDto::new).toList();
				var orderHistory = new OrderHistoryDto(s.getId(), s.getStatus(), saleItemList);
				orderList.add(orderHistory);
			});;
		}
		
		return orderList;
	}
}
