package com.jdc.online.balances.controller.management;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jdc.online.balances.controller.management.dto.AdminHomeVo;
import com.jdc.online.balances.controller.management.dto.LineChartVo;
import com.jdc.online.balances.service.AdminChartDataService;
import com.jdc.online.balances.service.MemberManagementService;
import com.jdc.online.balances.utils.LoadType;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("admin/home")
@RequiredArgsConstructor
public class AdminHomeController {
	
	private final MemberManagementService memberService;
	private final AdminChartDataService chartService;

	@GetMapping
	public String index(ModelMap model) {
		var lastMonth = memberService.findMemberCount(LocalDate.now().minusMonths(1));
		var lastYear = memberService.findMemberCount(LocalDate.now().minusYears(1));
		var totalMember = memberService.findMemberCount(null);
		
		model.put("vo", new AdminHomeVo(lastMonth, lastYear, totalMember));
		return "management/home";
	}
	
	@ResponseBody
	@GetMapping("load")
	List<LineChartVo> loadData(@RequestParam(required = false, defaultValue = "Monthly") LoadType type) {
		return chartService.loadData(type);
	}
}
