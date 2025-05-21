package com.jdc.online.balances.controller.member.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ChartBalanceVo(
		LocalDate date,
		BigDecimal incomes,
		BigDecimal expenses) {

}
