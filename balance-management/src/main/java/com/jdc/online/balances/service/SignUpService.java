package com.jdc.online.balances.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jdc.online.balances.controller.anonymous.dto.SignUpForm;
import com.jdc.online.balances.model.entity.Account;
import com.jdc.online.balances.model.entity.Member;
import com.jdc.online.balances.model.entity.MemberActivity;
import com.jdc.online.balances.model.entity.consts.MemberStatus;
import com.jdc.online.balances.model.entity.consts.Role;
import com.jdc.online.balances.model.repo.AccountRepo;
import com.jdc.online.balances.model.repo.MemberActivityRepo;
import com.jdc.online.balances.model.repo.MemberRepo;
import com.jdc.online.balances.utils.exceptions.AppBusinessException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignUpService {
	
	private final AccountRepo accRepo;	
	private final MemberRepo memberRepo;
	private final MemberActivityRepo memberActivityRepo;
	private final PasswordEncoder passEncoder;
	private final AccessHistoryService accessHistoryService;
	
	
	public String signUp(SignUpForm form) {
		
		if(accRepo.existsById(form.getUserName())) {
			throw new AppBusinessException("Account with this email is already registered.");
		}
		
		var account = new Account();
		account.setUserName(form.getUserName());
		account.setPassword(passEncoder.encode(form.getPassword()));
		account.setRole(Role.Member);
		account.setActive(true);
		
		account = accRepo.save(account);
		
		var member = new Member();
		member.setAccount(account);
		member.setName(form.getName());
		member.setEmail(form.getUserName());
		
		memberRepo.save(member);
		
		var memberActivity = new MemberActivity();
		memberActivity.setMember(member);
		memberActivity.setBalance(BigDecimal.ZERO);
		memberActivity.setRegisteredAt(LocalDateTime.now());
		memberActivity.setLastAccessedAt(LocalDateTime.now());
		memberActivity.setStatus(MemberStatus.Active);
		
		memberActivityRepo.save(memberActivity);
		
		accessHistoryService.signUp(form.getUserName());
		
		return "Your account is successfully registered. Please sign in with that email.";
	}
}
