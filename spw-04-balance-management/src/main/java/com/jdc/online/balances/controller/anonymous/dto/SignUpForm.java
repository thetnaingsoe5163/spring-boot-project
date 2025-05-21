package com.jdc.online.balances.controller.anonymous.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignUpForm {
	
	@NotBlank(message = "Please enter name.")
	private String name;
	
	@NotBlank(message = "Please enter email for login.")
	private String userName;
	
	@NotBlank(message = "Please set the password.")
	private String password;
}
