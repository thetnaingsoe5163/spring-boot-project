package com.jdc.online.balances.controller.member.dto;

import java.time.LocalDate;
import java.util.ArrayList;

import com.jdc.online.balances.model.entity.Account_;
import com.jdc.online.balances.model.entity.LedgerEntry;
import com.jdc.online.balances.model.entity.LedgerEntry_;
import com.jdc.online.balances.model.entity.Ledger_;
import com.jdc.online.balances.model.entity.Member_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;

@Data	
public class BalanceSearch {

	private LocalDate dateFrom;
	private LocalDate dateTo;
	
	public Predicate[] where(CriteriaBuilder cb, Root<LedgerEntry> root, String username) {
		var list = new ArrayList<Predicate>();
		
		list.add(
				cb.equal(root.get(LedgerEntry_.ledger).get(Ledger_.member)
						.get(Member_.account).get(Account_.userName), username));
		
		if(dateFrom != null) {
			list.add(cb.greaterThanOrEqualTo(root.get(LedgerEntry_.issuedAt), dateFrom.atStartOfDay()));
		}
		if(dateTo != null) {
			list.add(cb.lessThanOrEqualTo(root.get(LedgerEntry_.issuedAt), dateTo.plusDays(1).atStartOfDay()));
		}
				
		return list.toArray(s -> new Predicate[s]);
	}
}
