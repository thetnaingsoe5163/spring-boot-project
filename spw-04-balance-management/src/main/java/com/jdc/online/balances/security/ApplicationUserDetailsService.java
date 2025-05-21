package com.jdc.online.balances.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jdc.online.balances.model.repo.AccountRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationUserDetailsService implements UserDetailsService {
	
	private final AccountRepo accountRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return accountRepo.findById(username)
				.map(a  -> User.withUsername(username)
						.password(a.getPassword())
						.authorities(a.getRole().name())
						.disabled(!a.isActive())
						.build())
				.orElseThrow(() -> new UsernameNotFoundException("Invalid user name: %s".formatted(username)));
	}

}
