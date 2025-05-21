package com.jdc.online.balances.utils.listeners;

import java.time.LocalDateTime;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationFailureDisabledEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.online.balances.model.entity.consts.Role;
import com.jdc.online.balances.service.AccessHistoryService;
import com.jdc.online.balances.service.MemberActivityService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthenticationEventListener {
	
	private final AccessHistoryService service;
	private final MemberActivityService activityService;

	@EventListener
	@Transactional
	public void handle(AuthenticationSuccessEvent event) {
		service.successLogin(event.getAuthentication().getName());
		
		var authentication = event.getAuthentication();
		if(authentication.getAuthorities().stream()
				.map(a -> a.getAuthority())
				.filter(s -> s.equals(Role.Member.name()))
				.count() > 0) {
			
			var username = authentication.getName();
			activityService.updateLastAccessedAt(LocalDateTime.now(), username);
		}
	}
	
	@EventListener
	public void handle(AbstractAuthenticationFailureEvent event) {
		
		var message = switch(event) {
		case AuthenticationFailureBadCredentialsEvent e-> "Invalid username or password.";
		case AuthenticationFailureDisabledEvent e -> "Account is disabled.";
		default -> "Authentication error.";
		};
		
		service.failLogin(event.getAuthentication().getName(), message);
	}
	
	@EventListener
	public void handle(LogoutSuccessEvent event) {
		service.successLogout(event.getAuthentication().getName());
	}
}
