package com.jdc.online.balances.controller.management;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jdc.online.balances.controller.management.dto.MemberSearch;
import com.jdc.online.balances.controller.management.dto.MemberStatusForm;
import com.jdc.online.balances.service.MemberManagementService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("admin/member")
@RequiredArgsConstructor
public class MemberManagementController {
	
	private final MemberManagementService service;

	@GetMapping
	String index(
			MemberSearch search,
			@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam(required = false, defaultValue = "10") int size,
			ModelMap model
			) {
		
		var result = service.search(search, page, size);
		model.put("result", result);
		
		return "management/members/list";
	}
	
	@GetMapping("{id}")
	String showDetails(
			@PathVariable int id,
			ModelMap model) {
		
		var result = service.findById(id);
		model.put("result", result);
		
		return "management/members/details";
	}
	
	@PostMapping("{id}/update")
	String updateStatus(
			ModelMap model,
			@PathVariable long id,
			@Validated MemberStatusForm form,
			BindingResult result) {
		
		if(result.hasErrors()) {
			return "management/members/details";
		}
		
		var details = service.findById(id); 
		service.updateStatus(id, form);
		
		return "redirect:/admin/member/%d".formatted(id);
	}
}
