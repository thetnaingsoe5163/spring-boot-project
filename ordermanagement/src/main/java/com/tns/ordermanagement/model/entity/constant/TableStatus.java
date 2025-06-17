package com.tns.ordermanagement.model.entity.constant;

import com.tns.ordermanagement.exception.AppBusinessException;

public enum TableStatus {

	Free, Occupied, Cleaning, Maintenance;

	public String getFullStatus() {
		return switch (this) {
		case Free -> "The table is free";
		case Occupied -> "The table is currently occupied";
		case Cleaning -> "The table is currently being cleaned.";
		case Maintenance -> "The table is currently under maintenance";
		default -> throw new AppBusinessException("Illegal Status");
		};
	}
}
