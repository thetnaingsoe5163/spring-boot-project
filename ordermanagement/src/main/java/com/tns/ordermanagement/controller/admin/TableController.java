package com.tns.ordermanagement.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tns.ordermanagement.service.TableService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("admin/table")
@RequiredArgsConstructor
public class TableController {

	private final TableService service;
	
}
