package com.jdc.online.balances.controller.member.dto;

import com.jdc.online.balances.model.entity.Ledger;
import com.jdc.online.balances.model.entity.consts.BalanceType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LedgerForm {

	private Integer id;
	
	@NotNull(message = "Please enter balance type.")
	private BalanceType type;
	@NotBlank(message = "Please enter ledger name.")
	private String name;
	
	private Boolean status;
	
	
	public static LedgerForm from(Ledger ledger) {
		return new LedgerForm(
							ledger.getId(),
							ledger.getType(),
							ledger.getName(),
							ledger.isDeleted());
	}
}
