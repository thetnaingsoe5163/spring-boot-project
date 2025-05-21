package com.jdc.online.balances.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.jdc.online.balances.controller.member.dto.ChartAmountVo;
import com.jdc.online.balances.controller.member.dto.ChartBalanceVo;
import com.jdc.online.balances.controller.member.dto.ChartSummaryVo;
import com.jdc.online.balances.model.entity.consts.BalanceType;
import com.jdc.online.balances.model.repo.LedgerEntryRepo;
import com.jdc.online.balances.utils.LoadType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberChartService {

	private final LedgerEntryRepo repo;
	
	public ChartSummaryVo getSummaryData(LoadType type) {
		var username = SecurityContextHolder.getContext().getAuthentication().getName();
		return switch(type) {
		case Monthly -> getMonthlySummaryData(username);
		case Yearly -> getYearlySummaryData(username);
		};
	}

	public List<ChartBalanceVo> getBalanceData(LoadType type) {
		
		var username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		return switch(type) {
		case Monthly -> getMonthlyBalanceData(username);
		case Yearly -> getYearlyBalanceData(username);
		};		
	}
	
	public List<ChartAmountVo> getLedgers(BalanceType balanceType, LoadType loadType) {
		
		return switch(loadType) {
		case Monthly -> getMonthlyLedgerData(balanceType);
		case Yearly -> getYearlyLedgerData(balanceType);
		};
	}
	
	private List<ChartAmountVo> getYearlyLedgerData(BalanceType balanceType) {
		
		var username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		var to = LocalDate.now();
		var from = to.minusYears(1);
		
		return repo.findLedgerData(balanceType, username, from, to);
	}

	private List<ChartAmountVo> getMonthlyLedgerData(BalanceType balanceType) {
		
		var username = SecurityContextHolder.getContext().getAuthentication().getName();
				
		var to = LocalDate.now();
		var from = to.minusMonths(1);
		
		return repo.findLedgerData(balanceType, username, from, to);	
	}

	private ChartSummaryVo getYearlySummaryData(String username) {
		var to = LocalDate.now();
		var from = to.minusYears(1);
		
		var incomes = repo.getSummaryData(BalanceType.Incomes, username, from, to);
		var expenses = repo.getSummaryData(BalanceType.Expenses, username, from, to);
		
		return new ChartSummaryVo(getValue(incomes), getValue(expenses));
	}

	private ChartSummaryVo getMonthlySummaryData(String username) {
		var to = LocalDate.now();
		var from = to.minusMonths(1);
		
		var incomes = repo.getSummaryData(BalanceType.Incomes, username, from, to);
		var expenses = repo.getSummaryData(BalanceType.Expenses, username, from, to);
		
		return new ChartSummaryVo(getValue(incomes), getValue(expenses));
	}
	
	private List<ChartBalanceVo> getYearlyBalanceData(String username) {
		var result = new ArrayList<ChartBalanceVo>();
		
		var from = LocalDate.now().minusYears(1);
		
		while(from.compareTo(LocalDate.now()) < 0) {
			var to = from.plusMonths(3);
			
			var incomes = repo.getSummaryData(BalanceType.Incomes, username, from, to);
			var expenses = repo.getSummaryData(BalanceType.Expenses, username, from, to);
			
			result.add(new ChartBalanceVo(to, incomes, expenses));
			
			from = to;
		}
		
		return result;
	}

	private List<ChartBalanceVo> getMonthlyBalanceData(String username) {
		var result = new ArrayList<ChartBalanceVo>();
		
		var to = LocalDate.now();
		var from = to.minusMonths(1);
		
		while(from.compareTo(to) < 0) {
			var incomes = repo.getSummaryData(BalanceType.Incomes, username, from, to);
			var expenses = repo.getSummaryData(BalanceType.Expenses, username, from, to);
			
			result.add(new ChartBalanceVo(to, incomes, expenses));
			from = from.plusMonths(1);
		}
		
		return result;
	}
	
	private BigDecimal getValue(BigDecimal value) {
		return Optional.ofNullable(value).orElse(BigDecimal.ZERO);
	}

}

