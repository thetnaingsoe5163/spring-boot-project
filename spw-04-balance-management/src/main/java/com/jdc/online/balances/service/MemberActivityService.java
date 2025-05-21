package com.jdc.online.balances.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.jdc.online.balances.model.repo.MemberActivityRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberActivityService {

	private final MemberActivityRepo repo;
	
	public int updateLastAccessedAt(LocalDateTime lastAccessedAt, String userName) {
		return repo.updateLastAccess(lastAccessedAt, userName);
	}
}
