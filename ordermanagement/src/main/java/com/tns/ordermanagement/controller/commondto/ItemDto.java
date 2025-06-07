package com.tns.ordermanagement.controller.commondto;

import java.util.List;

import com.tns.ordermanagement.model.entity.Item;

public record ItemDto(
		Integer id,
		Integer categoryId,
		String categoryName,
		String englishName,
		String burmeseName,
		String description,
		String image,
		List<String> ingredients
		) {
	
	public ItemDto(Item item) {
		this(
				item.getId(),
				item.getCategory().getId(),
				item.getCategory().getName(),
				item.getEnglishName(),
				item.getBurmeseName(),
				item.getDescription(),
				item.getImage(),
				item.getIngredients());
	}
}
