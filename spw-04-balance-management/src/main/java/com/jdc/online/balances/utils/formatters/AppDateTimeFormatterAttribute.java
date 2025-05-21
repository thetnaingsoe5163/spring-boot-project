package com.jdc.online.balances.utils.formatters;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class AppDateTimeFormatterAttribute {

	@ModelAttribute("dtf")
	public AppDateTimeFormatter dtfFormatter() {
		return new AppDateTimeFormatter();
	}
}
