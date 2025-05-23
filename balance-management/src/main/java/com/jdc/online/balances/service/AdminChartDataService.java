package com.jdc.online.balances.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.jdc.online.balances.controller.management.dto.LineChartVo;
import com.jdc.online.balances.model.entity.AccessHistory;
import com.jdc.online.balances.model.entity.AccessHistory_;
import com.jdc.online.balances.model.entity.embeddables.AccessHistoryPk_;
import com.jdc.online.balances.model.repo.AccessHistoryRepo;
import com.jdc.online.balances.utils.LoadType;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminChartDataService {

	private final AccessHistoryRepo accessHistory;
	
	public List<LineChartVo> loadData(LoadType type) {
		return switch(type) {
		case Monthly -> loadMonthlyData();
		case Yearly -> loadYearlyData();
		};
	}

	private List<LineChartVo> loadYearlyData() {
		var result = new ArrayList<LineChartVo>();
		
		var endMonth = YearMonth.now();
		var startMonth = endMonth.minusYears(1);
		
		while(startMonth.compareTo(endMonth) <= 0) {
			var date = startMonth.atDay(1);
			var from = date.atStartOfDay();
			var to = date.plusMonths(1).atStartOfDay();
			
			result.add(getData(date, from, to));
			
			startMonth = startMonth.plusMonths(1);
		}
		return result;
	}

	private List<LineChartVo> loadMonthlyData() {
		var result = new ArrayList<LineChartVo>();
		
		var endDate = LocalDate.now();
		var startDate = endDate.minusMonths(1);
		
		while(startDate.compareTo(endDate) <= 0) {
			result.add(getData(startDate, startDate.atStartOfDay(), startDate.plusDays(1).atStartOfDay()));
			startDate = startDate.plusDays(1);
		}
		return result;
	}

	private LineChartVo getData(LocalDate startDate, LocalDateTime from, LocalDateTime to) {
		
		Function<LocalDateTime, Instant> convertInstant = ldt -> ldt.atZone(ZoneId.systemDefault()).toInstant();
		
		Function<CriteriaBuilder, CriteriaQuery<Long>> queryFunc = cb -> {
			var cq = cb.createQuery(Long.class);
			var root = cq.from(AccessHistory.class);
			
			cq.select(cb.count(root.get(AccessHistory_.id)));
			
			var param = new Predicate[2];
			param[0] = cb.greaterThanOrEqualTo(root.get(AccessHistory_.id).get(AccessHistoryPk_.accessedAt), convertInstant.apply(from));
			param[1] = cb.lessThanOrEqualTo(root.get(AccessHistory_.id).get(AccessHistoryPk_.accessedAt), convertInstant.apply(to));
			
			cq.where(param);
			return cq;
		};
		
		var value = accessHistory.count(queryFunc);
		
		return new LineChartVo(startDate, value);
	}
	
}
