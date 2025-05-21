package com.jdc.online.balances.utils.formatters;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AppDateTimeFormatter {

	private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd EEE");
	
	public String formatLocalDateTime(LocalDateTime ldt) {
		if(ldt != null) {
			return ldt.format(dtf);
		}
		return null;
	}
	
	public String formatLocalDate(LocalDate ld) {
		if(ld != null) {
			return ld.format(df);
		}
		return null;
	}
}
