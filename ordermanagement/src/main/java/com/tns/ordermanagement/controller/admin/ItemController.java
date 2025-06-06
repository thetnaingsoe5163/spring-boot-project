package com.tns.ordermanagement.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tns.ordermanagement.controller.admin.dto.AddItemForm;
import com.tns.ordermanagement.controller.admin.dto.NewCategoryForm;
import com.tns.ordermanagement.service.admin.CategoryService;
import com.tns.ordermanagement.service.admin.ItemService;

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
			@ModelAttribute("addItemForm") @Validated AddItemForm form,
			BindingResult result) {
		
		itemService.insert(form);
		return "redirect:/admin/item/new";
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
