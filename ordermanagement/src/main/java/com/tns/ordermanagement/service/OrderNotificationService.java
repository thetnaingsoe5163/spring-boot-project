package com.tns.ordermanagement.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.tns.ordermanagement.controller.admin.dto.SaleDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderNotificationService {

	private final SimpMessagingTemplate template;
	
	public void notifyNewOrder(SaleDto form) {
		template.convertAndSend("/topic/orders", form);
	}
}
