package com.gp.vaadin.vaadin_spring_jpa_demo.ui.view;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.gp.vaadin.vaadin_spring_jpa_demo.model.entity.HotelCategoryEntity;
import com.gp.vaadin.vaadin_spring_jpa_demo.model.filter.Filter;
import com.gp.vaadin.vaadin_spring_jpa_demo.model.filter.HotelCategoryFilter;
import com.gp.vaadin.vaadin_spring_jpa_demo.service.EntityService;
import com.gp.vaadin.vaadin_spring_jpa_demo.ui.form.AbstractForm;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;

@SpringView(name = HotelCategoryView.VIEW_NAME)
public class HotelCategoryView extends AbstractView<HotelCategoryEntity> {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4743969174479993938L;
	
	public static final String VIEW_NAME = "category";

	public static final String VIEW_CAPTION = "Category";
	
    private TextField nameFilter = new TextField();

	@Override
	protected void buildGrid(Grid<HotelCategoryEntity> grid) {
		grid.addColumn(HotelCategoryEntity::getName).setCaption("Name");
	}

	@Override
	protected void buildFilters() {
		nameFilter.setPlaceholder("filter by name...");
        nameFilter.addValueChangeListener(e -> updateList());
        nameFilter.setValueChangeMode(ValueChangeMode.LAZY);
	}

	@Override
	protected Collection<Component> getFilterComponents() {
		return Arrays.asList(nameFilter);
	}

	@Override
	protected String getEditButtonCaption() {
		return "Edit category";
	}

	@Override
	protected String getDeleteButtonCaption() {
		return "Delete category";
	}

	@Override
	protected String getAddButtonCaption() {
		return "Add category";
	}

	@Override
	protected Filter createFilter() {
		return new HotelCategoryFilter(nameFilter.getValue());
	}
	
	@Autowired
    @Override
    public void setService(@Qualifier("hotelCategoryService") EntityService<HotelCategoryEntity, Filter> service) {
    	super.setService(service);
    }

	@Autowired
	@Override
	public void setForm(@Qualifier("hotelCategoryForm") AbstractForm<HotelCategoryEntity> form) {
		super.setForm(form);
	}
    
}
