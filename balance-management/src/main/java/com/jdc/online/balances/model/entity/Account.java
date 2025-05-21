package com.jdc.online.balances.model.entity;

import com.jdc.online.balances.model.entity.consts.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Account {
	
	@Id
	private String userName;
	
	@Column(nullable = false)
	private String password;

	private boolean active;
	
	@Column(nullable = false)
	private Role role;

}