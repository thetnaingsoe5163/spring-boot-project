package com.tns.ordermanagement.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tns.ordermanagement.controller.admin.dto.SignUpForm;
import com.tns.ordermanagement.exception.AppBusinessException;
import com.tns.ordermanagement.model.entity.Account;
import com.tns.ordermanagement.model.repo.AccountRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	private final AccountRepo repo;
	private final PasswordEncoder passwordEncoder;
	
	public void signUp(SignUpForm form) {
		if(isExist(form.getUsername())) {
			throw new AppBusinessException("Account's already existed.");
		}
		var acc = new Account();
		acc.setUserName(form.getUsername());
		acc.setPassword(passwordEncoder.encode(form.getPassword()));
		
		repo.save(acc);
	}
	
	private boolean isExist(String username) {
		return repo.findById(username).isPresent();
	}
}
