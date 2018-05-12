package com.gp.vaadin.vaadin_spring_jpa_demo.bulk_update;

import com.gp.vaadin.vaadin_spring_jpa_demo.ui.form.HotelAddressValidator;
import com.vaadin.data.Validator;

@org.springframework.stereotype.Component
public class AddressBulkUpdateField extends AbstractTextBulkUpdateField {
	
	private final Validator<String> validator = new HotelAddressValidator();

	public Validator<String> getValidator() {
		return validator;
	}
}
