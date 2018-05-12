package com.gp.vaadin.vaadin_spring_jpa_demo.bulk_update;

import com.vaadin.data.ValidationResult;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextArea;

@org.springframework.stereotype.Component
public class TextAreaBulkUpdateField implements BulkUpdateField {

	@Override
	public Component createValueComponent() {
		TextArea textArea = new TextArea();
		return textArea;
	}

	@Override
	public ValidationResult validate(Component component) {
		return ValidationResult.ok();
	}
	
	@Override
	public Object extractValue(Component component) {
		TextArea textArea = (TextArea) component;
		return textArea.getValue();
	}
	
}
