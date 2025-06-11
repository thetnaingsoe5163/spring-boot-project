package com.tns.ordermanagement.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tns.ordermanagement.model.entity.constant.Status;
import com.tns.ordermanagement.service.SaleService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class Home {
	
	private final SaleService saleService;
	
	@GetMapping
	String index(ModelMap model) {
		var sales = saleService.findByStatus(Status.Pending);
		model.addAttribute("sales", sales);
		return "admin/home";
	}
}
