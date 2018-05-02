package com.gp.vaadin.vaadin_spring_jpa_demo.ui;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.gp.vaadin.vaadin_spring_jpa_demo.model.entity.HotelCategoryEntity;
import com.gp.vaadin.vaadin_spring_jpa_demo.model.entity.HotelEntity;
import com.gp.vaadin.vaadin_spring_jpa_demo.model.filter.HotelCategoryFilter;
import com.gp.vaadin.vaadin_spring_jpa_demo.service.HotelCategoryService;
import com.gp.vaadin.vaadin_spring_jpa_demo.service.HotelService;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.ValidationResult;
import com.vaadin.data.Validator;
import com.vaadin.data.ValueContext;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

@SpringComponent
@ViewScope
public class HotelForm extends FormLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4404488930218649725L;
	
	private TextField name = new TextField("Name");
    private TextField address = new TextField("Address");
    private TextField rating = new TextField("Rating");
    private DateField operatesFrom = new DateField("Operates from");
    
    private ComboBox<HotelCategoryEntity> category = new ComboBox<>("Category");
    private TextField url = new TextField("Url");
    private TextArea description = new TextArea("Description");
    
    private Button save = new Button("Save");
    private Button close = new Button("Close");

    @Autowired
    private HotelService service;
    
    @Autowired
    private HotelCategoryService categoryService;
    
    private HotelEntity hotel;
    
    private HotelView hotelUI;
    
    private Binder<HotelEntity> binder = new Binder<>(HotelEntity.class);

    @PostConstruct
    public void init() {

        setSizeUndefined();
        HorizontalLayout buttons = new HorizontalLayout(save, close);
        
        addComponents(name, address, rating, category, operatesFrom, url, description, buttons);
        
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(KeyCode.ENTER);

        category.setItemCaptionGenerator(category -> category.getName());
        
        prepareFields();

        save.addClickListener(e -> this.save());
        close.addClickListener(e -> this.close());
    }

    private void prepareFields() {
    	binder.forField(name).asRequired("Please enter a name").bind(HotelEntity::getName, HotelEntity::setName);
    	name.setDescription("Hotel name");
    	
    	Validator<String> hotelAddressValidator = new Validator<String>() {
    		/**
			 * 
			 */
			private static final long serialVersionUID = -4919218331954565599L;

			@Override
    		public ValidationResult apply(String value, ValueContext context) {
    			if (value == null || value.isEmpty()) {
    				return ValidationResult.error("The address is empty");
    			}
    			if (value.length() < 5) {
    				return ValidationResult.error("The address is too short");
    			}
    			return ValidationResult.ok();
    		}
		};
		binder.forField(address).asRequired("Please enter a address").withValidator(hotelAddressValidator).bind(HotelEntity::getAddress, HotelEntity::setAddress);
		address.setDescription("Hotel address");
		
		binder.forField(rating).asRequired("Please enter a rating").withConverter(
			    new StringToIntegerConverter("Must be a number")).withValidator(rating -> rating >= 0 && rating <= 5,
			    	    "Rating shoul be between 0 and 5").bind(HotelEntity::getRating, HotelEntity::setRating);
		rating.setDescription("Hotel rating");
		
		binder.forField(operatesFrom).asRequired("Please enter a operates from date")
				.withValidator(date -> date.isBefore(LocalDate.now()), "Operates from date should be in past")
				.bind(hotel -> hotel.getOperatesFrom() == null ? null : LocalDateTime
						.ofInstant(Instant.ofEpochMilli(hotel.getOperatesFrom()), ZoneId.systemDefault()).toLocalDate(),
						(hotel, localDate) -> hotel.setOperatesFrom(
								localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()));
		operatesFrom.setDescription("Hotel operates from date");
		
		binder.forField(category).withNullRepresentation(HotelCategoryEntity.empty()).asRequired("Please enter a category").bind(hotel -> categoryService.getById(hotel.getCategoryId()), (hotel, hotelCategory) -> hotel.setCategoryId(hotelCategory.getId()));
		category.setDescription("Hotel category");
		
		binder.forField(url).asRequired("Please enter a url").bind(HotelEntity::getUrl, HotelEntity::setUrl);
		url.setDescription("Hotel url");
		
		binder.forField(description).bind(HotelEntity::getDescription, HotelEntity::setDescription);
		description.setDescription("Hotel description");
	}

	public void setHotel(HotelEntity hotel) {
        this.hotel = hotel;
        
        binder.readBean(hotel);

        setVisible(true);
        name.selectAll();
    }

    private void close() {
    	hotelUI.updateList();
        setVisible(false);
        hotelUI.onCloseEditForm();
    }

    private void save() {
    	
    	if (binder.isValid()) {
    		try {
    			binder.writeBean(hotel);
    			service.save(hotel);
    		} catch (ValidationException e) {
    			Notification.show("Unable to save." + e.getMessage(), Type.HUMANIZED_MESSAGE);
    		} finally {
    			close();
    		}
    	} else {
    		Notification.show("Unable to save.", Type.HUMANIZED_MESSAGE);
    	}
        
    }

	public void onInitialize(HotelView hotelView) {
		this.hotelUI = hotelView;
		category.setItems(categoryService.findAll(new HotelCategoryFilter()));
	}

	
}
