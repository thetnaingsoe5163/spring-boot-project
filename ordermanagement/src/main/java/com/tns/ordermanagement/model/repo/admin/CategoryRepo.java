package com.tns.ordermanagement.model.repo.admin;

import java.util.Optional;

import com.tns.ordermanagement.model.entity.Category;
import com.tns.ordermanagement.model.repo.BaseRepo;

public interface CategoryRepo extends BaseRepo<Category, Integer> {

	Optional<Category> findByName(String name);
}
