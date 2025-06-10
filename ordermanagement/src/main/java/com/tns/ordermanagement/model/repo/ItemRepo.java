package com.tns.ordermanagement.model.repo;

import java.util.Optional;

import com.tns.ordermanagement.model.entity.Item;

public interface ItemRepo extends BaseRepo<Item, Integer> {

	Optional<Item> findByEnglishName(String name);
}
