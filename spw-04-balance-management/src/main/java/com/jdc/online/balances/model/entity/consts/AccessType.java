package com.jdc.online.balances.model.entity.consts;

public enum AccessType {
	Login, Logout, Signup {
		@Override
		public String getName() {
			return "Sign Up";
		}	
	}, Timeout;
	
	public String getName() {
		return this.name();
	}
}
