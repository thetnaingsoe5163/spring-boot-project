package com.jdc.online.balances.controller.member.dto;

import java.math.BigDecimal;

public record ChartAmountVo(
		String ledger,
		BigDecimal value
		) {

}
