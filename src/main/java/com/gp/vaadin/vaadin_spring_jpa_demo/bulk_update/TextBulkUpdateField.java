package com.gp.vaadin.vaadin_spring_jpa_demo.bulk_update;

import com.vaadin.data.Validator;
import com.vaadin.data.validator.StringLengthValidator;

@org.springframework.stereotype.Component
public class TextBulkUpdateField extends AbstractTextBulkUpdateField {
	
	private final Validator<String> validator = new StringLengthValidator("The value is empty", 1, null);

	@Override
	protected Validator<String> getValidator() {
		return validator;
	}
}
