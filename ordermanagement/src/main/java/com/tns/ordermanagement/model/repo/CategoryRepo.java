package com.tns.ordermanagement.model.repo;

import java.util.Optional;

import com.tns.ordermanagement.model.entity.Category;

public interface CategoryRepo extends BaseRepo<Category, Integer> {

	Optional<Category> findByName(String name);
}
