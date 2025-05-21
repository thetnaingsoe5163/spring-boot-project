package com.jdc.online.balances.service;

import static com.jdc.online.balances.utils.EntityOperation.safeCall;

import java.time.LocalDate;
import java.util.function.Function;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.online.balances.controller.management.dto.MemberDetails;
import com.jdc.online.balances.controller.management.dto.MemberListItem;
import com.jdc.online.balances.controller.management.dto.MemberSearch;
import com.jdc.online.balances.controller.management.dto.MemberStatusForm;
import com.jdc.online.balances.model.PageResult;
import com.jdc.online.balances.model.entity.Member;
import com.jdc.online.balances.model.entity.MemberActivity_;
import com.jdc.online.balances.model.entity.Member_;
import com.jdc.online.balances.model.entity.consts.MemberStatus;
import com.jdc.online.balances.model.repo.MemberActivityRepo;
import com.jdc.online.balances.model.repo.MemberRepo;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberManagementService {
	
	private static final String RESOURCE_NAME = "member";
	private static final String KEY_NAME = "id";
	
	private final MemberRepo memberRepo;
	private final MemberActivityRepo memberActivityRepo;

	public PageResult<MemberListItem> search(MemberSearch search, int page, int size) {
		return memberRepo.search(queryFunc(search), countFunc(search), page, size);
	}

	public MemberDetails findById(long id) {
		var memeber = memberRepo.findById(id);
		var temp = memberRepo.findById(id).map(MemberDetails::from);
		return safeCall(
				memberRepo.findById(id).map(MemberDetails::from), 
				RESOURCE_NAME, KEY_NAME, id);
	}

	@Transactional
	public void updateStatus(long id, MemberStatusForm form) {
		var member = safeCall(memberRepo.findById(id), RESOURCE_NAME, KEY_NAME, id);
		
		member.getAccount().setActive(form.getStatus());
		
		var activity = member.getActivity();
		activity.setStatus(form.getStatus() ? MemberStatus.Active : MemberStatus.Denied);
		activity.setReasonForStatusChange(form.getReason());
	}
	
	public Long findMemberCount(LocalDate dateFrom) {
		if(dateFrom == null) {
			return memberRepo.count();
		}
		return memberActivityRepo.countByRegisteredAtIsGreaterThanEqual(dateFrom.atStartOfDay());
	}
	
	private Function<CriteriaBuilder, CriteriaQuery<MemberListItem>> queryFunc(MemberSearch search) {
		return cb -> {
			var cq = cb.createQuery(MemberListItem.class);
			var root = cq.from(Member.class);
			
			MemberListItem.select(cq, root);
			cq.where(search.where(cb, root));
			
			cq.orderBy(cb.desc(root.get(Member_.activity).get(MemberActivity_.updatedAt)));  
			
			return cq;
		};
	}
	
	private Function<CriteriaBuilder, CriteriaQuery<Long>> countFunc(MemberSearch search) {
		return cb -> {
			var cq = cb.createQuery(Long.class);
			var root = cq.from(Member.class);
			
			cq.select(cb.count(root.get(Member_.id)));
			search.where(cb, root);
			
			return cq;
		};
	}
}	
