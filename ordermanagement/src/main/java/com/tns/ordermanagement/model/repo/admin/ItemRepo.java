package com.tns.ordermanagement.model.repo.admin;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tns.ordermanagement.model.entity.Item;

public interface ItemRepo extends JpaRepository<Item, Integer> {

}
