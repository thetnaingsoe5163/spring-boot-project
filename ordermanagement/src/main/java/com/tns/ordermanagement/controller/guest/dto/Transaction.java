package com.tns.ordermanagement.controller.guest.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class Transaction {
	
	private List<UUID> ids = new ArrayList<>();
}
