package com.tns.ordermanagement.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tns.ordermanagement.controller.admin.dto.AddItemForm;
import com.tns.ordermanagement.controller.admin.dto.NewCategoryForm;
import com.tns.ordermanagement.controller.commondto.ItemDto;
import com.tns.ordermanagement.service.CategoryService;
import com.tns.ordermanagement.service.ItemService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("admin/item")
@RequiredArgsConstructor
public class ItemController {

	private final CategoryService categoryService;
	private final ItemService itemService;
	
	@GetMapping("new")
	String newItem(ModelMap model) {
		var categories = categoryService.findAll();
		model.addAttribute("categories", categories);
		
		return "admin/create-item";
	}
	
	@PostMapping("new/category")
	String newCategory(
			ModelMap model,
			@Validated NewCategoryForm form, 
			BindingResult result) {
		
		if(result.hasErrors()) {
			model.addAttribute("result", result);
			model.addAttribute("openModal", true);
			var rawRequestPath = form.getRequestMadePath();
			
			return "%s".formatted(getValidRequestPath(rawRequestPath)); 
		}
		
		categoryService.addNewCategory(form);
		return "redirect:/admin"; 
	}
	
	@PostMapping
	String save(
			HttpServletRequest request,
			ModelMap model,
			@ModelAttribute("addItemForm") @Validated AddItemForm form,
			BindingResult result) {
		
		if(result.hasErrors()) {
			var categories = categoryService.findAll();
			model.addAttribute("categories", categories);
			return "admin/create-item";
		}
		itemService.insert(form, request);
		return "redirect:/admin/item/new";
	}
	
	@GetMapping("edit/{categoryId}")
	String edit(ModelMap model, @PathVariable(required = true) int categoryId) {
		var categories = categoryService.findAll();
		model.addAttribute("categories", categories);
		
		List<ItemDto> items = null;
		if(categoryId == 0) {
			items = itemService.findAll();
		} else {
			model.put("selectedCategoryId", categoryId);
			items = itemService.findByCategoryId(categoryId);
		}
		
		model.put("items", items);
		return "admin/items-edition";
	}
	
	@ModelAttribute("addItemForm")
	AddItemForm addItemForm() {
		return new AddItemForm();
	}
	
	private String getValidRequestPath(String rawRequestPath) {
		var result = new StringBuffer();
		var arr = rawRequestPath.split("/");
		
		for(var i = 2; i < arr.length; i++) {
			if(i == arr.length-1) {
				var temp = arr[i].split("\\.");
				result.append(temp[0]);
				
			} else {
				result.append(arr[i]).append("/");
			}
		}
		
		return result.toString();
	}
}
