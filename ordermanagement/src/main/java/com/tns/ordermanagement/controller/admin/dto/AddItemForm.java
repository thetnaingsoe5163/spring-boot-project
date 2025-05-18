package com.tns.ordermanagement.controller.admin.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class AddItemForm {

	private int category;
	private String englishName;
	private String burmeseName;
	private String description;
	
	private List<String> ingredients = new ArrayList<>();
}
