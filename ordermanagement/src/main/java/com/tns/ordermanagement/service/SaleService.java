package com.tns.ordermanagement.service;

import org.springframework.stereotype.Service;

import com.tns.ordermanagement.model.repo.SaleRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaleService {

	private final SaleRepo repo;
}
