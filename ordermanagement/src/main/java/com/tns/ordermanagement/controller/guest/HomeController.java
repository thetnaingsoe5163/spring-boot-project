package com.tns.ordermanagement.controller.guest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	@GetMapping("{tableNumber}")
	String index(
			@PathVariable int tableNumber,
			ModelMap model) {
		var categories = categoryService.findAll();
		var items = itemService.findAll();
		
		model.put("tableNumber", tableNumber);
		model.put("categories", categories);
		model.put("items", items);
		return "guest/home";
	}
	
	@GetMapping("items/{tableNumber}/{id}")
	String itemsByCategory(
			ModelMap model,
			@PathVariable int tableNumber,
			@PathVariable int id) {
		
		var categories = categoryService.findAll();
		var items = itemService.findByCategoryId(id);
		
		model.put("tableNumber", tableNumber);
		model.put("categories", categories);
		model.put("items", items);
		
		return "guest/home";
	}
}
