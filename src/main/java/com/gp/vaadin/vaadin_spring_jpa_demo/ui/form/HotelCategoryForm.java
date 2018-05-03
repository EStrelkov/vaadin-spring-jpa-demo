package com.gp.vaadin.vaadin_spring_jpa_demo.ui.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.gp.vaadin.vaadin_spring_jpa_demo.model.entity.HotelCategoryEntity;
import com.gp.vaadin.vaadin_spring_jpa_demo.model.filter.Filter;
import com.gp.vaadin.vaadin_spring_jpa_demo.service.EntityService;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.TextField;

@SpringComponent
@ViewScope
public class HotelCategoryForm extends AbstractForm<HotelCategoryEntity> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4404488930218649725L;
	
	private TextField name = new TextField("Name");

	@Override
	protected void addAndPrepareFormComponents() {
		addComponent(name);
		
		binder.forField(name).asRequired("Please enter a name").bind(HotelCategoryEntity::getName, HotelCategoryEntity::setName);
    	name.setDescription("Hotel category name");
	}

	@Override
	protected Class<HotelCategoryEntity> getEntityClass() {
		return HotelCategoryEntity.class;
	}

	@Autowired
	@Override
	public void setService(@Qualifier("hotelCategoryService") EntityService<HotelCategoryEntity, Filter> service) {
		super.setService(service);
	}
	
}
