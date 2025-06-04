package com.tns.ordermanagement.service.admin;

import org.springframework.stereotype.Service;

import com.tns.ordermanagement.controller.admin.dto.NewCategoryForm;
import com.tns.ordermanagement.exception.AppBusinessException;
import com.tns.ordermanagement.model.entity.Category;
import com.tns.ordermanagement.model.repo.admin.CategoryRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

	private final CategoryRepo repo;
	
	public void addNewCategory(NewCategoryForm form) {
		var oldCategory = repo.findByName(form.getName());
		
		if(!oldCategory.isEmpty()) {
			throw new AppBusinessException("Category already exists.");
		}
		
		var newCategory = new Category();
		newCategory.setName(form.getName());
		
		repo.save(newCategory);
	}
}
