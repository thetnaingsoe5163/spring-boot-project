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

import com.tns.ordermanagement.controller.admin.dto.ItemEditingForm;
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
			@ModelAttribute("itemEditingForm") @Validated ItemEditingForm form,
			BindingResult result) {
		
		if(result.hasErrors()) {
			var categories = categoryService.findAll();
			model.addAttribute("categories", categories);
			return "admin/create-item";
		}
		
		if(form.getItemId() != null) {
			System.out.println(form);
			itemService.update(form, request);
			return "redirect:/admin/item/%d".formatted(form.getCategory());
		} 
		
		itemService.insert(form, request);
		return "redirect:/admin/item/new";
	}
	
	@GetMapping("{categoryId}")
	String itemsByCategory(ModelMap model, @PathVariable(required = true) int categoryId) {
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
	
	@GetMapping("edit/{id}")
	String editItem(
			@ModelAttribute("itemEditingForm") ItemEditingForm form,
			@PathVariable int id, ModelMap model) {
		form = itemService.getEditFormById(id, form);
		model.put("form", form);
		var categories = categoryService.findAll();
		model.addAttribute("categories", categories);
		
		return "admin/create-item";
	}
	
	@GetMapping("delete/{id}")
	String deleteItem(@PathVariable int id, HttpServletRequest request) {
		itemService.deleteItemById(id, request);
		return "redirect:/admin/item/0";
	}
	
	@GetMapping("details/{id}")
	String details(@PathVariable int id, ModelMap model) {
		var item = itemService.findByItemId(id);
		model.put("item", item);
		return "admin/item-details";
	}
	
	@ModelAttribute("itemEditingForm")
	ItemEditingForm itemEditingForm() {
		return new ItemEditingForm();
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
