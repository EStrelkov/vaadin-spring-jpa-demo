package com.gp.vaadin.vaadin_spring_jpa_demo.ui.form;

import com.vaadin.data.ValidationResult;
import com.vaadin.data.Validator;
import com.vaadin.data.ValueContext;

public class HotelAddressValidator implements Validator<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2794317197580733670L;

	@Override
	public ValidationResult apply(String value, ValueContext context) {
		if (value == null || value.isEmpty()) {
			return ValidationResult.error("The address is empty");
		}
		if (value.length() < 5) {
			return ValidationResult.error("The address is too short");
		}
		return ValidationResult.ok();
	}
	
}
