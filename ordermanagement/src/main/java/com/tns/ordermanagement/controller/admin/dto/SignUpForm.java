package com.tns.ordermanagement.controller.admin.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SignUpForm {

	@NotEmpty(message = "Username cannot be empty!")
	private String username;
	
	@NotEmpty(message = "Password cannot be empty!")
	private String password;
}
