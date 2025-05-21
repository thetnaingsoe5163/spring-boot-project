package com.tns.ordermanagement.model.entity;

import java.util.List;

import com.tns.ordermanagement.utils.converter.IngredientListConverter;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Item extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String englishName;
	private String burmeseName;
	private String description;
	
	@Convert(converter = IngredientListConverter.class)
	private List<String> ingredients;
}
