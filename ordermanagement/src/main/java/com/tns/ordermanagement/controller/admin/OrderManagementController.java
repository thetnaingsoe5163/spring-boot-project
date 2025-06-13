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

import com.tns.ordermanagement.controller.admin.dto.OrderSubmitForm;
import com.tns.ordermanagement.service.SaleService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("admin/order")
@RequiredArgsConstructor
public class OrderManagementController {

	private final SaleService saleService;
	
	@GetMapping("details/{id}")
	String details(@PathVariable UUID id, ModelMap model) {
		var items = saleService.findItemsBySaleId(id);
		
		var orderItems = new ArrayList<>(items.stream().map(i -> i.convertToOrderItem()).toList());
		var orderForm = new OrderSubmitForm();
		orderForm.setId(orderItems.get(0).getSaleId());
		orderForm.setOrderItems(orderItems);
		
		model.addAttribute("form", orderForm);
		return "admin/order-details";
	}
	
	@GetMapping("immediate-approve/{id}")
	String orderApproveAll(@PathVariable("id") UUID id) {
		System.out.println(id);
		
		saleService.approveOrder(id);
		return "redirect:/admin";
	}
	
	@PostMapping("approve")
	String orderApprove(@ModelAttribute("form") OrderSubmitForm form) {
		saleService.approveOrder(form);
		return "redirect:/admin";
	}
	
	@ModelAttribute("form")
	OrderSubmitForm getForm() {
		return new OrderSubmitForm();
	}
}
