package com.tns.ordermanagement.controller.admin.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddItemForm {

	@NotNull(message = "Category must not be null.")
	private Integer category;
	
	private String englishName;
	private String burmeseName;
	private String description;
	
	private List<String> ingredients = new ArrayList<>();
	private MultipartFile imageFile;
}
