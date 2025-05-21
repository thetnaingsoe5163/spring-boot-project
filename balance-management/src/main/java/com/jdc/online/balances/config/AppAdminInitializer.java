package com.jdc.online.balances.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.online.balances.model.entity.Account;
import com.jdc.online.balances.model.entity.consts.Role;
import com.jdc.online.balances.model.repo.AccountRepo;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Configuration
@PropertySource(value = "classpath:/app-admin.properties")
@PropertySource(value = "classpath:/patch.properties")
@RequiredArgsConstructor
public class AppAdminInitializer {
	
	private final PasswordEncoder passEncoder;
	private final AccountRepo accountRepo;
	
	@Value("${app.admin.user-name}")
	private String userName;
	@Value("${app.admin.password}")
	private String password;
	
	@PostConstruct
	@Transactional
	public void initialize() {
		if(accountRepo != null) {
			var adminAcc = new Account();
			adminAcc.setUserName(userName);
			adminAcc.setPassword(passEncoder.encode(password));
			adminAcc.setRole(Role.Admin);
			adminAcc.setActive(true);
			
			accountRepo.save(adminAcc);
		}
	}

}
