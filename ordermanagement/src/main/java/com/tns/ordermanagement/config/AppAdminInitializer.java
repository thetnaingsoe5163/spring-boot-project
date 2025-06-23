package com.tns.ordermanagement.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.tns.ordermanagement.model.entity.Account;
import com.tns.ordermanagement.model.entity.constant.Role;
import com.tns.ordermanagement.model.repo.AccountRepo;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@PropertySource(value = "classpath:/app-admin.properties")
public class AppAdminInitializer {
	
	private final AccountRepo repo;
	private final PasswordEncoder encoder;

	@Value("${app.admin.username}")
	private String username;
	
	@Value("${app.admin.password}")
	private String password;
	
	@PostConstruct
	@Transactional
	private void initialize() {
		if(repo.findById(username).isEmpty()) {
			var account = new Account();
			account.setUserName(username);
			account.setPassword(encoder.encode(password));
			account.setRole(Role.Admin);
			
			repo.save(account);
		}
	}
}
