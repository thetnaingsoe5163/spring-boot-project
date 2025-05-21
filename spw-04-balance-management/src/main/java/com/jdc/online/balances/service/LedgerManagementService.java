package com.jdc.online.balances.service;

import static com.jdc.online.balances.utils.EntityOperation.safeCall;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.online.balances.controller.member.dto.LedgerForm;
import com.jdc.online.balances.controller.member.dto.LedgerListItem;
import com.jdc.online.balances.controller.member.dto.LedgerSearch;
import com.jdc.online.balances.controller.member.dto.LedgerSelectItem;
import com.jdc.online.balances.model.PageResult;
import com.jdc.online.balances.model.entity.Account_;
import com.jdc.online.balances.model.entity.Ledger;
import com.jdc.online.balances.model.entity.Ledger_;
import com.jdc.online.balances.model.entity.Member_;
import com.jdc.online.balances.model.entity.consts.BalanceType;
import com.jdc.online.balances.model.repo.LedgerRepo;
import com.jdc.online.balances.model.repo.MemberRepo;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LedgerManagementService {
	
	private final LedgerRepo ledgerRepo;
	private final MemberRepo memberRepo;
	
	@Transactional
	public void save(LedgerForm form) {
	
		var entity = Optional.ofNullable(form.getId())
				.flatMap(i -> ledgerRepo.findById(i))
				.orElse(new Ledger());
		
		entity.setName(form.getName());
		entity.setType(form.getType());
		entity.setDeleted(form.getStatus());
		
		if(entity.getMember() == null) {
			var username = SecurityContextHolder.getContext().getAuthentication().getName();
			var member = safeCall(memberRepo.findOneByAccountUserName(username), "Member", "id", username);
			entity.setMember(member);
		}
		
		ledgerRepo.save(entity);
	}
	
	public LedgerForm findForLedgerEditForm(Integer id) {
		return ledgerRepo.findById(id).map(LedgerForm::from).orElse(new LedgerForm());
	}
	
	@PreAuthorize("#username eq authentication.name")
	public PageResult<LedgerListItem> search(String username, LedgerSearch form, int page, int size) {
		return ledgerRepo.search(queryFunc(form, username), countFunc(form, username), page, size);
	}
	
	@PreAuthorize("#username eq authentication.name")
	public List<LedgerSelectItem> findForEntry(String username, BalanceType type) {
		return ledgerRepo.search(queryFunc(username, type));
	}
	
	private Function<CriteriaBuilder, CriteriaQuery<LedgerListItem>> queryFunc(LedgerSearch form, String username) {
		return cb -> {
			var cq = cb.createQuery(LedgerListItem.class);
			var root = cq.from(Ledger.class);
			
			LedgerListItem.select(cb, cq, root);
			cq.where(form.where(cb, root, username));
			
			cq.orderBy(cb.asc(root.get(Ledger_.id)));
			
			return cq;
		};
	}

	private Function<CriteriaBuilder, CriteriaQuery<Long>> countFunc(LedgerSearch form, String username) {
		return cb -> {
			var cq = cb.createQuery(Long.class);
			var root = cq.from(Ledger.class);
			
			cq.select(cb.count(root.get(Ledger_.id)));
			cq.where(form.where(cb, root, username));
			
			return cq;
		};
	}
	
	private Function<CriteriaBuilder, CriteriaQuery<LedgerSelectItem>> queryFunc(String username, BalanceType type) {
		return cb -> {
			var cq = cb.createQuery(LedgerSelectItem.class);
			var root = cq.from(Ledger.class);
			
			cq.multiselect(
					root.get(Ledger_.id),
					root.get(Ledger_.name));
			
			cq.where(
					cb.equal(root.get(Ledger_.member).get(Member_.account).get(Account_.userName), username),
					cb.equal(root.get(Ledger_.type), type));
			
			cq.orderBy(cb.desc(root.get(Ledger_.name)));
			
			return cq;
		};
	}
}
