package com.tns.ordermanagement.service;

import org.springframework.stereotype.Service;

import com.tns.ordermanagement.model.repo.SaleItemRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaleItemService {

	private final SaleItemRepo repo;
}
