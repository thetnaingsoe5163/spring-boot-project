package com.jdc.online.balances.service;

import java.time.Instant;
import java.util.function.Function;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.online.balances.controller.member.dto.AccessHistoryListItem;
import com.jdc.online.balances.controller.member.dto.AccessHistorySearch;
import com.jdc.online.balances.model.PageResult;
import com.jdc.online.balances.model.entity.AccessHistory;
import com.jdc.online.balances.model.entity.AccessHistory_;
import com.jdc.online.balances.model.entity.consts.AccessStatus;
import com.jdc.online.balances.model.entity.consts.AccessType;
import com.jdc.online.balances.model.entity.embeddables.AccessHistoryPk;
import com.jdc.online.balances.model.entity.embeddables.AccessHistoryPk_;
import com.jdc.online.balances.model.repo.AccessHistoryRepo;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccessHistoryService {
	
	private final AccessHistoryRepo repo;

	@Transactional
	public void signUp(String username) {
		createHistory(username, AccessType.Signup, AccessStatus.Success, null);
	}
	
	@Transactional
	public void successLogin(String name) {
		createHistory(name, AccessType.Login, AccessStatus.Success, null);
	}

	@Transactional
	public void successLogout(String name) {
		createHistory(name, AccessType.Logout, AccessStatus.Success, null);
	}
	
	public void failLogin(String name, String message) {
		createHistory(name, AccessType.Login, AccessStatus.Fail, message);
	}
	
	public PageResult<AccessHistoryListItem> search(AccessHistorySearch form,
			int page, int size) {
		return repo.search(queryFunc(form), countFunc(form), page, size);
	}
	
	private Function<CriteriaBuilder, CriteriaQuery<AccessHistoryListItem>> queryFunc(AccessHistorySearch form) {
		return cb -> {
			var cq = cb.createQuery(AccessHistoryListItem.class);
			var root = cq.from(AccessHistory.class);
			
			AccessHistoryListItem.select(cq, root);
			cq.where(form.where(cb, root));
			cq.orderBy(cb.desc(root.get(AccessHistory_.id).get(AccessHistoryPk_.accessedAt)));
			
			return cq;
		};
	}
	
	private Function<CriteriaBuilder, CriteriaQuery<Long>> countFunc(AccessHistorySearch form) {
		return cb -> {
			var cq = cb.createQuery(Long.class);
			var root = cq.from(AccessHistory.class);
			
			cq.select(cb.count(root.get(AccessHistory_.id)));
			cq.where(form.where(cb, root));
			
			return cq;
		};
	}
	
	private void createHistory(String username, AccessType type, AccessStatus status, String message) {
		
		var accessHistoryPk = new AccessHistoryPk();
		accessHistoryPk.setUsername(username);
		accessHistoryPk.setAccessedAt(Instant.now());
		
		var accessHistory = new AccessHistory();
		accessHistory.setId(accessHistoryPk);
		accessHistory.setType(type);
		accessHistory.setStatus(status);
		
		if(message != null) {
			accessHistory.setRemark(message);
		}
		
		repo.save(accessHistory);
	}
}
