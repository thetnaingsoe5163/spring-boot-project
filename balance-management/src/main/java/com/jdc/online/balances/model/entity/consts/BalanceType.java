package com.jdc.online.balances.model.entity.consts;

public enum BalanceType {

	Incomes, Expenses;
	
	public static BalanceType from(String type) {
		return switch(type) {
		case "incomes" -> Incomes;
		case "expenses" -> Expenses;
		default -> throw new IllegalArgumentException("Invalid Value %s".formatted(type));
		};
	}
	
	public String to() {
		return this.name().toLowerCase();
	}
}
