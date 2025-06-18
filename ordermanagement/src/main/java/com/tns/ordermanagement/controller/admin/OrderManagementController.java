package com.tns.ordermanagement.controller.admin;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tns.ordermanagement.controller.admin.dto.OrderSubmitForm;
import com.tns.ordermanagement.service.OrderTransactionService;
import com.tns.ordermanagement.service.ReceiptGeneratorService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("admin/order")
@RequiredArgsConstructor
public class OrderManagementController {

	private final OrderTransactionService orderTrxService;
	private final ReceiptGeneratorService receiptGenerator;
	
	@GetMapping("details/{id}")
	String details(@PathVariable UUID id, ModelMap model, RedirectAttributes attr) {
		var items = orderTrxService.findIncomingItemsByOrderId(id);
		
		var orderItems = new ArrayList<>(items.stream().map(i -> i.convertToOrderItem()).toList());
		var orderForm = new OrderSubmitForm();
		
		if(orderItems.isEmpty()) {
			attr.addFlashAttribute("message", "No order from %s currently.".formatted(id));
			return "redirect:/admin";
		}
		orderForm.setId(orderItems.get(0).getSaleId());
		orderForm.setOrderItems(orderItems);
		
		model.addAttribute("form", orderForm);
		return "admin/order-details";
	}
	
	@GetMapping("immediate-approve/{id}")
	String immediatelyApprove(@PathVariable("id") UUID id, RedirectAttributes attr) {
		orderTrxService.approveWithoutChecking(id);
		attr.addFlashAttribute("message", "Order %s is approved.".formatted(id));
		return "redirect:/admin";
	}
	
	@GetMapping("check/{id}")
	String checkOrder(@PathVariable UUID id, ModelMap model) {
		var order = orderTrxService.findOrder(id);
		model.put("order", order);
		
		return "admin/check-details";
	}
//	
	@PostMapping("approve")
	String orderApprove(@ModelAttribute("form") OrderSubmitForm form) {
		orderTrxService.approve(form);
		return "redirect:/admin";
	}
	
	@GetMapping("pay/{id}")
	String payBill(@PathVariable UUID id, ModelMap model) {
		var receipt = orderTrxService.payBill(id);
		receiptGenerator.generateReceipt(receipt);
		model.addAttribute("receipt", receipt);
		return "admin/payment";
	}
}
