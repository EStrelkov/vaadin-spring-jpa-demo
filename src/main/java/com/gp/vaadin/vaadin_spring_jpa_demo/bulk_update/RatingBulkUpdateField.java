package com.gp.vaadin.vaadin_spring_jpa_demo.bulk_update;

import com.vaadin.data.ValidationResult;
import com.vaadin.data.Validator;
import com.vaadin.data.ValueContext;
import com.vaadin.ui.Component;

@org.springframework.stereotype.Component
public class RatingBulkUpdateField extends AbstractTextBulkUpdateField {

	private Validator<String> validator = new Validator<String>() {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 815134640961524719L;

		@Override
		public ValidationResult apply(String value, ValueContext context) {
			
			try {
				int rating = Integer.parseInt(value);
				if (rating < 0 || rating > 5) {
					return ValidationResult.error("Rating should be between 0 and 5");
				}
			} catch (NumberFormatException e) {
				return ValidationResult.error("Must be a number");
			}
			
			return ValidationResult.ok();
		}
	};
	
	@Override
	public Object extractValue(Component component) {
		return Integer.parseInt(super.extractValue(component).toString());
	}

	@Override
	protected Validator<String> getValidator() {
		return validator;
	}

}
