package com.tns.ordermanagement.service.admin;

import org.springframework.stereotype.Service;

import com.tns.ordermanagement.controller.admin.dto.NewCategoryForm;
import com.tns.ordermanagement.model.repo.admin.CategoryRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

	private final CategoryRepo repo;
	
	public void addNewCategory(NewCategoryForm form) {
		
	}
}
