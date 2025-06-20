package com.tns.ordermanagement.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tns.ordermanagement.model.entity.constant.OrderTransactionStatus;
import com.tns.ordermanagement.service.OrderTransactionService;
import com.tns.ordermanagement.service.TableService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class Home {
	
	private final OrderTransactionService trxService;
	private final TableService tableService;
	
	@GetMapping
	String index(ModelMap model) {
		var orders = trxService.findByStatus(OrderTransactionStatus.INPROGRESS);
		var tables = tableService.findAll();
		
		model.addAttribute("orders", orders);
		model.addAttribute("tables", tables);
		return "admin/home";
	}
}
