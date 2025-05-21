package com.jdc.online.balances.service;

import static com.jdc.online.balances.utils.EntityOperation.safeCall;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jdc.online.balances.controller.member.dto.LedgerEntryForm;
import com.jdc.online.balances.controller.member.dto.LedgerEntryListItem;
import com.jdc.online.balances.controller.member.dto.LedgerEntrySearch;
import com.jdc.online.balances.model.PageResult;
import com.jdc.online.balances.model.entity.LedgerEntry;
import com.jdc.online.balances.model.entity.LedgerEntryItem;
import com.jdc.online.balances.model.entity.LedgerEntry_;
import com.jdc.online.balances.model.entity.Member;
import com.jdc.online.balances.model.entity.consts.BalanceType;
import com.jdc.online.balances.model.entity.embeddables.LedgerEntryItemPk;
import com.jdc.online.balances.model.entity.embeddables.LedgerEntryPk;
import com.jdc.online.balances.model.repo.LedgerEntryItemRepo;
import com.jdc.online.balances.model.repo.LedgerEntryRepo;
import com.jdc.online.balances.model.repo.LedgerRepo;
import com.jdc.online.balances.model.repo.MemberRepo;
import com.jdc.online.balances.utils.exceptions.AppBusinessException;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberLedgerEntryService {
	
	private final LedgerEntryRepo entryRepo;
	private final LedgerEntryItemRepo itemRepo;
	private final LedgerEntryIdGenerator idGenerator;
	private final LedgerRepo ledgerRepo;
	private final MemberRepo memberRepo;

	@PreAuthorize("authentication.name eq #username")
	public PageResult<LedgerEntryListItem> search(String username, BalanceType type, LedgerEntrySearch search, int page, int size) {
		return entryRepo.search(queryFunc(username, type, search), countFunc(username, type, search), page, size);
	}
	
	public List<LedgerEntryListItem> searchAll() {
		return entryRepo.findAll().stream().map(LedgerEntryListItem::from).toList();
	}

	public LedgerEntryForm findForEdit(String id) {
		var username = SecurityContextHolder.getContext().getAuthentication().getName();
		var member = safeCall(memberRepo.findOneByAccountUserName(username), 
				"Member", "id", username);
		
		var memberId = member.getId();
		var ledgerEntryPk = LedgerEntryPk.parse(id, memberId); 
		
		var entryForm = safeCall(entryRepo.findById(ledgerEntryPk).map(LedgerEntryForm::from),
				"Ledger Entry", "id", id);
		
		return entryForm;
	}
	
	@Transactional
	public String save(LedgerEntryForm form) {
		var username = SecurityContextHolder.getContext().getAuthentication().getName();
		var member = safeCall(memberRepo.findOneByAccountUserName(username), "Member", "username", username);
		
		if(StringUtils.hasLength(form.getId())) {
			return update(form, member);
		}
		
		return insert(form, member);
	}

	private String insert(LedgerEntryForm form, Member member) {
		var id = idGenerator.next(member.getId(), LocalDate.now()); 
		
		var ledgerEntry = new LedgerEntry();
		ledgerEntry.setId(id);
		ledgerEntry.setParticular(form.getParticular()); 
		ledgerEntry.setLedger(ledgerRepo.findById(form.getLedgerId()).get());
		ledgerEntry.setIssuedAt(LocalDateTime.now());
		
		var lastAmount = Optional.ofNullable(member.getActivity().getBalance())
				.orElse(BigDecimal.ZERO);
		
		BigDecimal amount = form.getItems().stream()
				.filter(i -> !i.isDeleted())
				.map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
				.reduce((a, b) -> a.add(b))
				.orElse(BigDecimal.ZERO);
		
		ledgerEntry.setAmount(amount);
		ledgerEntry.setLastAmount(lastAmount);
		
		ledgerEntry = entryRepo.save(ledgerEntry);
		
		var length = form.getItems().size();
		for(var i = 0; i < length; i++) {
			
			var formItem = form.getItems().get(i);
			if(!formItem.isDeleted()) {
				var pk = new LedgerEntryItemPk();
				pk.setMemberId(ledgerEntry.getId().getMemberId());
				pk.setIssuedDate(ledgerEntry.getId().getIssuedDate());
				pk.setSeqNumber(ledgerEntry.getId().getSeqNumber());
				pk.setItemNumber(i + 1);
				
				var item = new LedgerEntryItem();
				item.setId(pk);
				item.setEntry(ledgerEntry);
				item.setItem(formItem.getItemName());
				item.setQuantity(formItem.getQuantity());
				item.setUnitPrice(formItem.getUnitPrice());
				
				itemRepo.save(item);
			}
		}
		
		var balance = switch(ledgerEntry.getLedger().getType()) {
		case Incomes -> ledgerEntry.getLastAmount().add(amount);
		case Expenses -> ledgerEntry.getLastAmount().subtract(amount);
		};
		
		member.getActivity().setBalance(balance);
		
		System.out.println("Ledger Entry Amount: " + ledgerEntry.getAmount());
		System.out.println("Last Amount: " + ledgerEntry.getLastAmount());
		System.out.println("Balance in Activity: " + member.getActivity().getBalance());
		return ledgerEntry.getId().getCode();
	}

	private String update(LedgerEntryForm form, Member member) {
		
		System.out.println("\nUpdating\n");
		
		var entryId = LedgerEntryPk.parse(form.getId(), member.getId());
		if(!entryId.getIssuedDate().equals(LocalDate.now())) {
			System.out.println("exception");
			throw new AppBusinessException("Old Ledger Entry cannot be edited.");
		}
		
		var entry = entryRepo.findById(entryId).get();
		
		// restoring original amount before editing target-edited entry
		var difference = entry.getLastAmount().subtract(member.getActivity().getBalance());
		member.getActivity().setBalance(difference.add(member.getActivity().getBalance()));
		
		System.out.println("Difference: " + difference);
		System.out.println("Original balance: " + member.getActivity().getBalance());

		entry.setLastAmount(member.getActivity().getBalance().subtract(entry.getLastAmount()));
		
		for(var item : entry.getItems()) {
			itemRepo.deleteById(item.getId());
		}
		
		for(var i = 0; i < form.getItems().size(); i++) {
			var item = form.getItems().get(i);
			
			if(!item.isDeleted()) {
				var pk = new LedgerEntryItemPk();
				pk.setMemberId(entry.getId().getMemberId());
				pk.setIssuedDate(entry.getId().getIssuedDate());
				pk.setSeqNumber(entry.getId().getSeqNumber());
				pk.setItemNumber(i + 1);
				
				var entryItem = new LedgerEntryItem();
				entryItem.setId(pk);
				entryItem.setEntry(entry);
				entryItem.setItem(item.getItemName());
				entryItem.setQuantity(item.getQuantity());
				entryItem.setUnitPrice(item.getUnitPrice());
				
				itemRepo.save(entryItem);
			}
		}
		
		entry.setParticular(form.getParticular()); 
		entry.setLedger(ledgerRepo.findById(form.getLedgerId()).get());
		
		var lastAmount = Optional.ofNullable(member.getActivity().getBalance())
				.orElse(BigDecimal.ZERO);
		
		BigDecimal amount = form.getItems().stream()
				.filter(i -> !i.isDeleted())
				.map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
				.reduce((a, b) -> a.add(b))
				.orElse(BigDecimal.ZERO);
		
		entry.setAmount(amount);
		entry.setLastAmount(lastAmount);
		
		var balance = switch(entry.getLedger().getType()) {
		case Incomes -> entry.getLastAmount().add(amount);
		case Expenses -> entry.getLastAmount().subtract(amount);
		};
		
		member.getActivity().setBalance(balance);
		
		var entries = entryRepo.findRemainingEntries(member.getId(), entry.getId().getIssuedDate(), entry.getId().getSeqNumber());
		for(var remainEntry : entries) {
			remainEntry.setLastAmount(member.getActivity().getBalance());
			
			var remainAmount = remainEntry.getItems().stream()
					.map(a -> a.getUnitPrice().multiply(BigDecimal.valueOf(a.getQuantity())))
					.reduce((a, b) -> a.add(b))
					.orElse(BigDecimal.ZERO);
			
			var remainBalance = switch(remainEntry.getLedger().getType()) {
			case Incomes -> remainEntry.getLastAmount().add(remainAmount);
			case Expenses -> remainEntry.getLastAmount().subtract(remainAmount);
			};
			
			member.getActivity().setBalance(remainBalance);
				
		}
		
		return entry.getId().getCode();
	}

	private Function<CriteriaBuilder, CriteriaQuery<Long>> countFunc(String username, BalanceType type, LedgerEntrySearch search) {
		return cb -> {
			var cq = cb.createQuery(Long.class);
			var root = cq.from(LedgerEntry.class);
			
			cq.select(cb.count(root.get(LedgerEntry_.id)));
			cq.where(search.where(username, type, cb, root));
			
			return cq;
		};
	}

	private Function<CriteriaBuilder, CriteriaQuery<LedgerEntryListItem>> queryFunc(String username, BalanceType type, LedgerEntrySearch search) {
		return cb -> {
			var cq = cb.createQuery(LedgerEntryListItem.class);
			var root = cq.from(LedgerEntry.class);
			
			LedgerEntryListItem.select(cq, root);
			cq.where(search.where(username, type, cb, root));
			
			cq.orderBy(cb.desc(root.get(LedgerEntry_.issuedAt)));
			
			return cq;
		};
	}
	
}
