package com.jdc.online.balances.controller.member.dto;

import java.util.ArrayList;

import org.springframework.util.StringUtils;

import com.jdc.online.balances.model.entity.Account_;
import com.jdc.online.balances.model.entity.Ledger;
import com.jdc.online.balances.model.entity.Ledger_;
import com.jdc.online.balances.model.entity.Member_;
import com.jdc.online.balances.model.entity.consts.BalanceType;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;

@Data
public class LedgerSearch {

	private BalanceType type;
	private String keyword;
	
	public Predicate[] where(CriteriaBuilder cb, Root<Ledger> root, String username) {
		var list = new ArrayList<Predicate>();
		
		list.add(cb.equal(root.get(Ledger_.member).get(Member_.account).get(Account_.userName), username));
		if(type != null) {
			list.add(cb.equal(root.get(Ledger_.type), type));
		}
		if(StringUtils.hasLength(keyword)) {
			list.add(cb.like(cb.lower(root.get(Ledger_.name)), keyword.toLowerCase().concat("%")));
		}
		
		return list.toArray(size -> new Predicate[size]);
	}
	
}
