package com.tns.ordermanagement.controller.guest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tns.ordermanagement.service.CategoryService;
import com.tns.ordermanagement.service.ItemService;

import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {
	
	private final CategoryService categoryService;
	private final ItemService itemService;

	@GetMapping
	String index(ModelMap model) {
		var categories = categoryService.findAll();
		var items = itemService.findAll();
		
		model.put("categories", categories);
		model.put("items", items);
		return "guest/home";
	}
	
}
