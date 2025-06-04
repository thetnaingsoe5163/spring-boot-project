package com.tns.ordermanagement.controller.admin.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NewCategoryForm {

	@NotBlank(message = "Please Enter Name.")
	private String name;
	
	@NotBlank(message = "Request Path must be included.")
	private String requestMadePath;
}
