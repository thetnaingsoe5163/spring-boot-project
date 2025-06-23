package com.tns.ordermanagement.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tns.ordermanagement.model.repo.AccountRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationUserDetailsService implements UserDetailsService {

	private final AccountRepo repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repo.findById(username)
				.map(u -> User.withUsername(username)
						.password(u.getPassword())
						.roles(u.getRole().name())
						.build())
				.orElseThrow(() -> new UsernameNotFoundException("Username %s is not found!".formatted(username)));
	}
}
