package com.jdc.online.balances.controller.member.dto;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.util.StringUtils;

import com.jdc.online.balances.model.entity.Account_;
import com.jdc.online.balances.model.entity.LedgerEntry;
import com.jdc.online.balances.model.entity.LedgerEntry_;
import com.jdc.online.balances.model.entity.Ledger_;
import com.jdc.online.balances.model.entity.Member_;
import com.jdc.online.balances.model.entity.consts.BalanceType;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;

@Data
public class LedgerEntrySearch {
	
	private LocalDate dateFrom;
	private LocalDate dateTo;
	private String keyword;
	
	public Predicate[] where(String username, BalanceType type, CriteriaBuilder cb, Root<LedgerEntry> root) {
		var list = new ArrayList<Predicate>();
		
		list.add(cb.equal(
				root.get(LedgerEntry_.ledger).get(Ledger_.member)
				.get(Member_.account).get(Account_.userName), username));
		
		list.add(cb.equal(root.get(LedgerEntry_.ledger).get(Ledger_.type), type));
		
		if(dateFrom != null) {
			list.add(cb.greaterThanOrEqualTo(root.get(LedgerEntry_.issuedAt), dateFrom.atStartOfDay()));
		}
		if(dateTo != null) {
			list.add(cb.lessThanOrEqualTo(root.get(LedgerEntry_.issuedAt), dateTo.plusDays(1).atStartOfDay()));
		}
		if(StringUtils.hasLength(keyword)) {
			list.add(cb.like(cb.lower(root.get(LedgerEntry_.particular)), keyword.toLowerCase().concat("%")));
		}
		
		return list.toArray(size -> new Predicate[size]);
	}
}
