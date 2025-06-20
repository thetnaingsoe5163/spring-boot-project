package com.tns.ordermanagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tns.ordermanagement.controller.admin.dto.TableDto;
import com.tns.ordermanagement.model.repo.RestaurantTableRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TableService {

	private final RestaurantTableRepo repo;

	public List<TableDto> findAll() {
		return repo.findAll().stream().map(TableDto::new).toList();
	}
}
