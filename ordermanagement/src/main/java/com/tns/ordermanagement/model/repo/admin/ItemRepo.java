package com.tns.ordermanagement.model.repo.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tns.ordermanagement.model.entity.Item;

public interface ItemRepo extends JpaRepository<Item, Integer> {

	Optional<Item> findByEnglishName(String name);
}
