package com.gp.vaadin.vaadin_spring_jpa_demo.bulk_update;

import com.vaadin.data.ValidationResult;
import com.vaadin.ui.Component;

public interface BulkUpdateField {

	Component createValueComponent();

	Object extractValue(Component component);

	ValidationResult validate(Component component);

}
