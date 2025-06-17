package com.tns.ordermanagement.model.repo;

import java.util.Optional;

import com.tns.ordermanagement.model.entity.RestaurantTable;

public interface RestaurantTableRepo extends BaseRepo<RestaurantTable, Integer> {

	Optional<RestaurantTable> findOneByTableNumber(int tableNumber);

}
