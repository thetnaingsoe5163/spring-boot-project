package com.jdc.online.balances.service;

import static com.jdc.online.balances.utils.EntityOperation.safeCall;

import java.util.function.Function;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.online.balances.controller.member.dto.BalanceListItem;
import com.jdc.online.balances.controller.member.dto.BalanceSearch;
import com.jdc.online.balances.controller.member.dto.LedgerEntryDetails;
import com.jdc.online.balances.model.PageResult;
import com.jdc.online.balances.model.entity.LedgerEntry;
import com.jdc.online.balances.model.entity.LedgerEntry_;
import com.jdc.online.balances.model.entity.embeddables.LedgerEntryPk;
import com.jdc.online.balances.model.repo.LedgerEntryRepo;
import com.jdc.online.balances.model.repo.MemberRepo;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberBalanceService {
	
	private final MemberRepo memberRepo;
	private final LedgerEntryRepo entryRepo;

	public LedgerEntryDetails findById(String id) {
		var username = SecurityContextHolder.getContext().getAuthentication().getName();
		var member = memberRepo.findOneByAccountUserName(username).get();
		
		var entryId = LedgerEntryPk.parse(id, member.getId());
		return safeCall(entryRepo.findById(entryId).map(LedgerEntryDetails::from), "Ledger Entry", "id", id);
	}

	@PreAuthorize("authentication.name eq #username")
	public PageResult<BalanceListItem> search(BalanceSearch search, String username, int page, int size) {
		return entryRepo.search(queryFunc(username, search), countFunc(username, search), page, size);
		
	}

	private Function<CriteriaBuilder, CriteriaQuery<BalanceListItem>> queryFunc(String username, BalanceSearch search) {
		return cb -> {
			var cq = cb.createQuery(BalanceListItem.class);
			var root = cq.from(LedgerEntry.class);
			
			BalanceListItem.select(cq, root);
			cq.where(search.where(cb, root, username));
			cq.orderBy(cb.desc(root.get(LedgerEntry_.issuedAt)));
			
			return cq;
		};
	}

	private Function<CriteriaBuilder, CriteriaQuery<Long>> countFunc(String username, BalanceSearch search) {
		return cb -> {
			var cq = cb.createQuery(Long.class);
			var root = cq.from(LedgerEntry.class);
			
			cq.select(cb.count(root.get(LedgerEntry_.id)));
			cq.where(search.where(cb, root, username));
			
			return cq;
		};
	}

}
