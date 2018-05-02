package com.gp.vaadin.vaadin_spring_jpa_demo.ui;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.gp.vaadin.vaadin_spring_jpa_demo.model.entity.HotelCategoryEntity;
import com.gp.vaadin.vaadin_spring_jpa_demo.service.HotelCategoryService;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

@SpringComponent
@ViewScope
public class HotelCategoryForm extends FormLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4404488930218649725L;
	
	private TextField name = new TextField("Name");
    
    private Button save = new Button("Save");
    private Button close = new Button("Close");

    @Autowired
    private HotelCategoryService categoryService;
    
    private HotelCategoryEntity hotelCategory;
    private HotelCategoryView hotelCategoryUI;
    private Binder<HotelCategoryEntity> binder = new Binder<>(HotelCategoryEntity.class);

    @PostConstruct
    public void init () {

        setSizeUndefined();
        HorizontalLayout buttons = new HorizontalLayout(save, close);
        
        addComponents(name, buttons);
        
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(KeyCode.ENTER);

        prepareFields();

        save.addClickListener(e -> this.save());
        close.addClickListener(e -> this.close());
    }

    private void prepareFields() {
    	binder.forField(name).asRequired("Please enter a name").bind(HotelCategoryEntity::getName, HotelCategoryEntity::setName);
    	name.setDescription("Hotel category name");
	}

	public void setHotelCategory(HotelCategoryEntity hotelCategory) {
        this.hotelCategory = hotelCategory;
        
        binder.readBean(hotelCategory);

        setVisible(true);
        name.selectAll();
    }

    private void close() {
    	hotelCategoryUI.updateList();
        setVisible(false);
        hotelCategoryUI.onCloseEditForm();
    }

    private void save() {
    	
    	if (binder.isValid()) {
    		try {
    			binder.writeBean(hotelCategory);
    			categoryService.save(hotelCategory);
    		} catch (ValidationException e) {
    			Notification.show("Unable to save." + e.getMessage(), Type.HUMANIZED_MESSAGE);
    		} finally {
    			close();
    		}
    	} else {
    		Notification.show("Unable to save.", Type.HUMANIZED_MESSAGE);
    	}
        
    }

	public void onInitialize(HotelCategoryView hotelCategoryView) {
		this.hotelCategoryUI = hotelCategoryView;
	}

	
}
