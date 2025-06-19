package com.tns.ordermanagement.controller.admin.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemEditingForm {
	
	private Integer itemId;
	
	@NotNull(message = "Please choose category.")
	private Integer category;
	
	@NotEmpty(message = "English name must not be empty.")
	private String englishName;
	
	@NotEmpty(message = "Burmese name must not be empty.")
	private String burmeseName;
	
	private String description;
	
	@NotNull(message = "Unit price must be set.")
	private Integer unitPrice;
	
	private List<String> ingredients = new ArrayList<>();
	private MultipartFile imageFile;
	
	private String imageName;

}
