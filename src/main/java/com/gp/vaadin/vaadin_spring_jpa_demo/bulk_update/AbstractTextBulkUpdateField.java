package com.gp.vaadin.vaadin_spring_jpa_demo.bulk_update;

import com.gp.vaadin.vaadin_spring_jpa_demo.util.ValidationUtils;
import com.vaadin.data.ValidationResult;
import com.vaadin.data.Validator;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;

public abstract class AbstractTextBulkUpdateField implements BulkUpdateField {

	@Override
	public Component createValueComponent() {
		TextField textField = new TextField();
		ValidationUtils.addValidator(textField, getValidator());
		return textField;
	}

	@Override
	public ValidationResult validate(Component component) {
		TextField textField = (TextField) component;
		return ValidationUtils.validateField(textField, textField.getValue(), getValidator());
	}
	
	@Override
	public Object extractValue(Component component) {
		TextField textField = (TextField) component;
		return textField.getValue();
	}
	
	protected abstract Validator<String> getValidator();
	
}
