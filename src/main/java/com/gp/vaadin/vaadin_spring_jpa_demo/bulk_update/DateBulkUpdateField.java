package com.gp.vaadin.vaadin_spring_jpa_demo.bulk_update;

import java.time.LocalDate;

import com.gp.vaadin.vaadin_spring_jpa_demo.util.DateUtils;
import com.gp.vaadin.vaadin_spring_jpa_demo.util.ValidationUtils;
import com.vaadin.data.ValidationResult;
import com.vaadin.data.Validator;
import com.vaadin.data.ValueContext;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;

@org.springframework.stereotype.Component
public class DateBulkUpdateField implements BulkUpdateField {

	private Validator<LocalDate> validator = new Validator<LocalDate>() {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -350523743081119637L;

		@Override
		public ValidationResult apply(LocalDate localDate, ValueContext context) {
			if (localDate.isBefore(LocalDate.now())) {
				return ValidationResult.ok();
			} else {
				return ValidationResult.error("Date should be in past");
			}
		}
	};

	@Override
	public Component createValueComponent() {
		return new DateField();
	}

	@Override
	public Object extractValue(Component component) {
		DateField dateField = (DateField) component;
		return DateUtils.getTime(dateField.getValue());
	}

	@Override
	public ValidationResult validate(Component component) {
		DateField dateField = (DateField) component;
		return ValidationUtils.validateField(dateField, dateField.getValue(), validator);
	}

}
