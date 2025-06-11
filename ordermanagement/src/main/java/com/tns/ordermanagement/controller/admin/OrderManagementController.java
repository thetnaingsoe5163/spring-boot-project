package com.tns.ordermanagement.controller.admin;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
		model.addAttribute("items", items);
		model.addAttribute("id", id);
		return "admin/order-details";
	}
	
	@GetMapping("approve/{id}")
	String orderApprove(@PathVariable("id") UUID id) {
		saleService.approveOrder(id);
		return "redirect:/admin";
	}
}
