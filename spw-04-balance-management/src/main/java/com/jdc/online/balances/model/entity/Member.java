package com.jdc.online.balances.model.entity;

import java.time.LocalDate;

import com.jdc.online.balances.model.entity.consts.Gender;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class Member extends AbstractEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@OneToOne(optional = false)
	private Account account;
	
	@Column(nullable = false)
	private String name;

	private String phone;
	
	@Column(nullable = false)
	private String email;

	private Gender gender;
	
	private LocalDate dob;
	
	private String profileImage;
	
	@ManyToOne
	private Township township;
	private String address; 
	
	@OneToOne(mappedBy = "member")
	private MemberActivity activity;
}