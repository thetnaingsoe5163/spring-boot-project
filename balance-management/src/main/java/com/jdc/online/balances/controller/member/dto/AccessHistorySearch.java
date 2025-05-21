package com.jdc.online.balances.controller.member.dto;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import org.springframework.util.StringUtils;

import com.jdc.online.balances.model.entity.AccessHistory;
import com.jdc.online.balances.model.entity.AccessHistory_;
import com.jdc.online.balances.model.entity.consts.AccessStatus;
import com.jdc.online.balances.model.entity.embeddables.AccessHistoryPk_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;

@Data
public class AccessHistorySearch {

	private String username;
	
	private AccessStatus status;
	private LocalDate dateFrom;
	private LocalDate dateTo;
	private String keyword;
	
	public Predicate[] where(CriteriaBuilder cb, Root<AccessHistory> root) {
		var predicate = new ArrayList<Predicate>();
		
		if(StringUtils.hasLength(username)) {
			predicate.add(cb.equal(root.get(AccessHistory_.id).get(AccessHistoryPk_.username), username));
		}
		if(status != null) {
			predicate.add(cb.equal(root.get(AccessHistory_.status), status));
		}
		if(dateFrom != null) {
			// dateFrom.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
			var instantTime = ZonedDateTime.of(dateFrom.atStartOfDay(), ZoneId.systemDefault()).toInstant(); 
			predicate.add(cb.greaterThanOrEqualTo(root.get(AccessHistory_.id).get(AccessHistoryPk_.accessedAt), 
					instantTime));
		}
		if(dateTo != null) {
			// dateTo.plusDays(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
			var instantTime = ZonedDateTime.of(dateTo.plusDays(1).atStartOfDay(), ZoneId.systemDefault()).toInstant(); 
			predicate.add(cb.lessThanOrEqualTo(root.get(AccessHistory_.id).get(AccessHistoryPk_.accessedAt),
					instantTime));
		}
		if(StringUtils.hasLength(keyword)) {
			predicate.add(cb.like(cb.lower(root.get(AccessHistory_.remark)), keyword.toLowerCase().concat("%")));
		}
			
		return predicate.toArray(size -> new Predicate[size]);
	}
}
