package com.tns.ordermanagement.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tns.ordermanagement.service.admin.CategoryService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("admin")
@RequiredArgsConstructor
public class ItemController {

	private final CategoryService categoryService;
	
	@GetMapping("new/category")
	String newCategory() {
		return "";
	}
	
	@PostMapping
	String save() {
		return "";
	}
}
