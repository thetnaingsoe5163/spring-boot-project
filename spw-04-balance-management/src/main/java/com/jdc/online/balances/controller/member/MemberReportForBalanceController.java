package com.jdc.online.balances.controller.member;

import java.time.LocalDate;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jdc.online.balances.controller.member.dto.BalanceSearch;
import com.jdc.online.balances.service.MemberBalanceService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("member/balance")
@RequiredArgsConstructor
public class MemberReportForBalanceController {
	
	private final MemberBalanceService service;

	@GetMapping
	public String index(
			ModelMap model,
			BalanceSearch search,
			@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam(required = false, defaultValue = "10") int size
			) {
		
		var username = SecurityContextHolder.getContext().getAuthentication().getName();
		var result = service.search(search, username, page, size);
		
		model.put("result", result);
		return "member/balance/list";
	}
	
	// yyyyMMdd-000
	@GetMapping("{id}")
	public String findByID(
			ModelMap model,
			@PathVariable String id) {
		
		var details = service.findById(id);
		var issuedAt = details.issuedAt().toLocalDate();
		var validEdit = issuedAt.equals(LocalDate.now());
		
		model.put("validEdit", validEdit);
		model.put("details", details);
		return "member/balance/details";
	}
}