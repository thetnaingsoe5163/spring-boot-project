package com.tns.ordermanagement.model.repo.admin;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tns.ordermanagement.model.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
