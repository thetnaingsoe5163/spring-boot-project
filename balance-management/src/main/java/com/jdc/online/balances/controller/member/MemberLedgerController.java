package com.jdc.online.balances.controller.member;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jdc.online.balances.controller.member.dto.LedgerForm;
import com.jdc.online.balances.controller.member.dto.LedgerSearch;
import com.jdc.online.balances.model.entity.consts.BalanceType;
import com.jdc.online.balances.service.LedgerManagementService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("member/ledger")
@RequiredArgsConstructor
public class MemberLedgerController {

	private final LedgerManagementService service;
	
	@GetMapping
	public String index(
			ModelMap model,
			LedgerSearch search,
			@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam(required = false, defaultValue = "10") int size
			) {
		
		var username = SecurityContextHolder.getContext().getAuthentication().getName();
		var result = service.search(username, search, page, size);
		
		model.put("search", search);
		model.put("result", result);
		
		return "member/ledgers/list";
	}
	
	@PostMapping
	String save(
			ModelMap model,
			@Validated @ModelAttribute("ledgerForm") LedgerForm form,
			BindingResult result
			) {
		
		if(result.hasErrors()) {
			return "member/ledgers/list";
		}
		service.save(form);
		
		return "redirect:/member/ledger";
	}
	
	@ModelAttribute("ledgerForm")
	LedgerForm ledgerForm(@RequestParam(required = false) Integer id) {
		if(id != null) {
			return service.findForLedgerEditForm(id);
		}	
		return new LedgerForm();
	}
	
	@ModelAttribute("balanceTypes")
	public BalanceType[] types() {
		return BalanceType.values();
	}
}
