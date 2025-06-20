package com.tns.ordermanagement.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.tns.ordermanagement.controller.admin.dto.OrderTrxDto;
import com.tns.ordermanagement.controller.admin.dto.TableDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {

	private final SimpMessagingTemplate template;
	
	public void notifyNewOrder(OrderTrxDto dto) {
		template.convertAndSend("/topic/orders", dto);
	}
	
	public void notifyTableStatusChanges(TableDto dto) {
		template.convertAndSend("/topic/tables", dto);
	}
}
