package com.tns.ordermanagement.controller.admin.dto;

import com.tns.ordermanagement.model.entity.Category;

public record CategoryDto(
		int id,
		String name
		) {

	public CategoryDto(Category category) {
		this(category.getId(), category.getName());
	}
}
