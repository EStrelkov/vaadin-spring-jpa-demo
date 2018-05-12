package com.gp.vaadin.vaadin_spring_jpa_demo.bulk_update;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import com.gp.vaadin.vaadin_spring_jpa_demo.ui.view.AbstractView.BulkUpdateCallback;
import com.vaadin.data.ValidationResult;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class BulkUpdateForm extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1844574178483519167L;

	private static final int BULK_UPDATE_VALUE_COMPONENT_POSITION = 1;

	private Map<String, BulkUpdateField> bulkUpdateFieldsConfig;

	public void onActivate(PopupView bulkUpdatePopup, BulkUpdateCallback bulkUpdateCallback) {

		removeAllComponents();

		ComboBox<String> fieldsComboBox = new ComboBox<>();
		Collection<String> items = new ArrayList<>(bulkUpdateFieldsConfig.size());

		for (Entry<String, BulkUpdateField> entry : bulkUpdateFieldsConfig.entrySet()) {
			items.add(entry.getKey());
		}

		fieldsComboBox.setEmptySelectionCaption("Please select field");
		fieldsComboBox.setItems(items);
		fieldsComboBox.addSelectionListener(event -> {
			removeComponent(getComponent(BULK_UPDATE_VALUE_COMPONENT_POSITION));
			if (event.getSelectedItem().isPresent()) {
				addComponent(bulkUpdateFieldsConfig.get(event.getSelectedItem().get()).createValueComponent(),
						BULK_UPDATE_VALUE_COMPONENT_POSITION);
			} else {
				addEmptyFieldValueComponent();
			}
		});

		addComponent(fieldsComboBox);

		addEmptyFieldValueComponent();

		HorizontalLayout toolbar = new HorizontalLayout();

		Button cancelBulkUpdateButton = new Button("Cancel");
		cancelBulkUpdateButton.addClickListener(e -> {
			bulkUpdatePopup.setPopupVisible(false);
		});

		Button doBulkUpdateButton = new Button("Update");
		doBulkUpdateButton.addClickListener(e -> {

			if (fieldsComboBox.getSelectedItem().isPresent()) {

				String selectedField = fieldsComboBox.getSelectedItem().get();

				Component component = getComponent(BULK_UPDATE_VALUE_COMPONENT_POSITION);

				BulkUpdateField bulkUpdateField = bulkUpdateFieldsConfig.get(selectedField);

				ValidationResult validationResult = bulkUpdateField.validate(component);

				if (!validationResult.isError()) {
					bulkUpdateCallback.doBulkUpdate(selectedField, bulkUpdateField.extractValue(component));

					bulkUpdatePopup.setPopupVisible(false);
				}
			}
		});

		toolbar.addComponents(doBulkUpdateButton, cancelBulkUpdateButton);

		addComponents(toolbar);
	}

	private void addEmptyFieldValueComponent() {
		addComponent(new TextField(), BULK_UPDATE_VALUE_COMPONENT_POSITION);
	}

	public void setBulkUpdateFieldsConfig(Map<String, BulkUpdateField> bulkUpdateFieldsConfig) {
		this.bulkUpdateFieldsConfig = bulkUpdateFieldsConfig;
	}

}
