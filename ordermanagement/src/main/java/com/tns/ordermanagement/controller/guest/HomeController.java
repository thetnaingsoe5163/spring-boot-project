package com.tns.ordermanagement.controller.guest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tns.ordermanagement.controller.guest.dto.OrderForm;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/")
public class HomeController {

	@GetMapping
	String index() {
		return "guest/home";
	}
	
	@ModelAttribute("orderForm")
	OrderForm getOrderForm() {
		return new OrderForm();
	}
	
}
