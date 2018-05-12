package com.gp.vaadin.vaadin_spring_jpa_demo.bulk_update;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.gp.vaadin.vaadin_spring_jpa_demo.model.entity.HotelCategoryEntity;
import com.gp.vaadin.vaadin_spring_jpa_demo.model.filter.HotelCategoryFilter;
import com.gp.vaadin.vaadin_spring_jpa_demo.service.EntityService;
import com.vaadin.data.ValidationResult;
import com.vaadin.server.UserError;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;

@org.springframework.stereotype.Component
public class HotelCategoryBulkUpdateField implements BulkUpdateField {

	@Autowired
	@Qualifier("hotelCategoryService")
	private EntityService<HotelCategoryEntity, HotelCategoryFilter> categoryService;
	
	@Override
	public Component createValueComponent() {
		ComboBox<HotelCategoryEntity> categoryComboBox = new ComboBox<>();
		
		categoryComboBox.setItems(categoryService.findAll(new HotelCategoryFilter()));
		categoryComboBox.addValueChangeListener(event -> {
			categoryComboBox.setComponentError(null);
        });
		
		return categoryComboBox;
	}

	@Override
	public Object extractValue(Component component) {
		@SuppressWarnings("unchecked")
		ComboBox<HotelCategoryEntity> categoryComboBox = (ComboBox<HotelCategoryEntity>) component;
		return categoryComboBox.getSelectedItem().get();
	}

	@Override
	public ValidationResult validate(Component component) {
		@SuppressWarnings("unchecked")
		ComboBox<HotelCategoryEntity> categoryComboBox = (ComboBox<HotelCategoryEntity>) component;
		
		if (categoryComboBox.getSelectedItem().isPresent()) {
			categoryComboBox.setComponentError(null);
			return ValidationResult.ok();
		} else {
			String errorMessage = "Please enter a category";
			UserError error = new UserError(errorMessage);
			categoryComboBox.setComponentError(error);
			return ValidationResult.error(errorMessage);
		}
		 
	}

}
