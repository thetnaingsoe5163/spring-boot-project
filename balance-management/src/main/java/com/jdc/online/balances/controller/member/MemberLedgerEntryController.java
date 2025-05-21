package com.jdc.online.balances.controller.member;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jdc.online.balances.controller.member.dto.LedgerEntryForm;
import com.jdc.online.balances.controller.member.dto.LedgerEntryFormItem;
import com.jdc.online.balances.controller.member.dto.LedgerEntrySearch;
import com.jdc.online.balances.controller.member.dto.LedgerSelectItem;
import com.jdc.online.balances.model.entity.consts.BalanceType;
import com.jdc.online.balances.service.LedgerManagementService;
import com.jdc.online.balances.service.MemberLedgerEntryService;
import com.jdc.online.balances.utils.exceptions.AppBusinessException;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("member/entry/{type}")
@RequiredArgsConstructor
public class MemberLedgerEntryController {
	
	private final MemberLedgerEntryService ledgerEntry;
	private final LedgerManagementService ledgerManagement;

	@GetMapping()
	String index(
			@PathVariable BalanceType type,
			LedgerEntrySearch search,
			@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam(required = false, defaultValue = "10") int size,
			ModelMap model) {
		
		var username = SecurityContextHolder.getContext().getAuthentication().getName();
		var result = ledgerEntry.search(username, type, search, page, size);		
		
		model.put("type", type);
		model.put("result", result);
		return "member/entries/list";
	}
	
	@GetMapping("create")
	String addNew(@PathVariable BalanceType type, ModelMap model) {
		model.put("type", type);
		return "member/entries/edit";
	}
	
	@GetMapping("edit")
	String edit(@RequestParam String id) {
		return "member/entries/edit";
	}
	
	@PostMapping("save")
	String save(
			ModelMap model,
			@PathVariable BalanceType type,
			@Validated @ModelAttribute(name = "form") LedgerEntryForm form,
			BindingResult result
			) {
		
		if(result.hasErrors()) {
			return "member/entries/edit";
		}
		
		try {
			var code = ledgerEntry.save(form); 
			return "redirect:/member/balance/%s".formatted(code);
		} catch (AppBusinessException e) {
			model.put("error", e.getMessage());
			return "member/entries/%s/edit".formatted(type.name().toLowerCase());
		}
	}
	
	@PostMapping("item/add")
	String addItem(@ModelAttribute(name = "form") LedgerEntryForm form) {
		form.getItems().add(new LedgerEntryFormItem());
		return "member/entries/edit";
	}
	
	@PostMapping("item/remove")
	String removeItem(@ModelAttribute(name = "form") LedgerEntryForm form) {
		return "member/entries/edit";
	}
	
	@ModelAttribute("form")
	LedgerEntryForm getLedgerEntryForm(
			@PathVariable BalanceType type, 
			@RequestParam(required = false) String id) {
		var form = new LedgerEntryForm();
		
		if(StringUtils.hasLength(id)) {
			form = ledgerEntry.findForEdit(id);
		}
		
		if(form.getItems() == null || form.getItems().isEmpty()) {
			form.getItems().add(new LedgerEntryFormItem());
		}
		
		return form;
	}
	
	@ModelAttribute("ledgers")
	List<LedgerSelectItem> getLedgers(@PathVariable BalanceType type) {
		var username = SecurityContextHolder.getContext().getAuthentication().getName();
		return ledgerManagement.findForEntry(username, type);
	}
}
