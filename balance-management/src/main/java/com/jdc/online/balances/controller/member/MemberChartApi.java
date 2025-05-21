package com.jdc.online.balances.controller.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jdc.online.balances.controller.member.dto.ChartAmountVo;
import com.jdc.online.balances.controller.member.dto.ChartBalanceVo;
import com.jdc.online.balances.controller.member.dto.ChartSummaryVo;
import com.jdc.online.balances.model.entity.consts.BalanceType;
import com.jdc.online.balances.service.MemberChartService;
import com.jdc.online.balances.utils.LoadType;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("member/chart")
@RequiredArgsConstructor
public class MemberChartApi {

	private final MemberChartService service;
	
	@GetMapping("summary")
	ChartSummaryVo getSummaryData(
			@RequestParam(required = false, defaultValue = "Monthly") LoadType type) {
		
		return service.getSummaryData(type);
	}
	
	@GetMapping("balance")
	List<ChartBalanceVo> getBalanceData(
			@RequestParam(required = false, defaultValue = "Monthly") LoadType type) {
		
		return service.getBalanceData(type);
	}
	
	@GetMapping("ledgers")
	Map<BalanceType, List<ChartAmountVo>> getLedgers(
			@RequestParam(required = false, defaultValue = "Monthly") LoadType type) {
		
		var incomes = service.getLedgers(BalanceType.Incomes, type);
		var expenses = service.getLedgers(BalanceType.Expenses, type);
		
		Map<BalanceType, List<ChartAmountVo>> map = new HashMap<>();
		map.put(BalanceType.Incomes, incomes);
		map.put(BalanceType.Expenses, expenses);
		
		return map;
		
//		Supplier<BigDecimal> generator = () -> BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble());
//		
//		map.put(BalanceType.Incomes,
//				IntStream.iterate(1, a -> a + 1).limit(5)
//				.mapToObj(a -> new ChartAmountVo("Incomes %d".formatted(a), generator.get()))
//				.toList());
//		
//		map.put(BalanceType.Expenses,
//				IntStream.iterate(1, a -> a + 1).limit(5)
//				.mapToObj(a -> new ChartAmountVo("Expenses %d".formatted(a), generator.get()))
//				.toList());
//		return map;
	}
}
