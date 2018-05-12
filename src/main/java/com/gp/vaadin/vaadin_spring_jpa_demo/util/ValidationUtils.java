package com.gp.vaadin.vaadin_spring_jpa_demo.util;

import com.vaadin.data.ValidationResult;
import com.vaadin.data.Validator;
import com.vaadin.data.ValueContext;
import com.vaadin.server.UserError;
import com.vaadin.ui.AbstractField;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ValidationUtils {
	
	public static void addValidator(AbstractField field, Validator validator) {
        field.addValueChangeListener(event -> {
        	validateField(field, field.getValue(), validator);
        });
    }
	
	public static ValidationResult validateField(AbstractField field, Object value, Validator validator) {
		ValidationResult result = validator.apply(value, new ValueContext(field));

        if (result.isError()) {
            UserError error = new UserError(result.getErrorMessage());
            field.setComponentError(error);
        } else {
            field.setComponentError(null);
        }
        
        return result;
	}

}
