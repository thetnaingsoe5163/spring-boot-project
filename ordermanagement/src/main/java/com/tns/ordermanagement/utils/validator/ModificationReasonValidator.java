package com.tns.ordermanagement.utils.validator;

import com.tns.ordermanagement.controller.admin.dto.OrderSubmitItem;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ModificationReasonValidator implements ConstraintValidator<ModificationReasonCrossValidator, OrderSubmitItem> {

	@Override
	public boolean isValid(OrderSubmitItem item, ConstraintValidatorContext context) {
		if(item == null) {
			return true;
		}
		if(item.isModified()) {
			return item.getReason() != null || !item.getReason().isBlank();
		}
		return true;
	}

}
