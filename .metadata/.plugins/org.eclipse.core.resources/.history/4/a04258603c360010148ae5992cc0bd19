package com.tns.ordermanagement.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class Home {
	
	@GetMapping
	String index() {
		return "admin/home";
	}

	@GetMapping("new/item")
	String newCategory() {
		return "admin/create-item";
	}
}
