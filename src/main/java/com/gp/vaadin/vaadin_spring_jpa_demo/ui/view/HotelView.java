package com.gp.vaadin.vaadin_spring_jpa_demo.ui.view;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.gp.vaadin.vaadin_spring_jpa_demo.model.entity.HotelEntity;
import com.gp.vaadin.vaadin_spring_jpa_demo.model.filter.Filter;
import com.gp.vaadin.vaadin_spring_jpa_demo.model.filter.HotelFilter;
import com.gp.vaadin.vaadin_spring_jpa_demo.service.EntityService;
import com.gp.vaadin.vaadin_spring_jpa_demo.ui.form.AbstractForm;
import com.gp.vaadin.vaadin_spring_jpa_demo.util.HotelCategoryUtils;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.renderers.HtmlRenderer;

@SpringView(name = HotelView.VIEW_NAME)
public class HotelView extends AbstractView<HotelEntity> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4471717033494212321L;
	
	public static final String VIEW_NAME = "hotel";

	public static final String VIEW_CAPTION = "Hotel";
	
    private TextField nameFilter = new TextField();
    private TextField addressFilter = new TextField();
    
	@Override
	protected void buildGrid(Grid<HotelEntity> grid) {
		grid.addColumn(HotelEntity::getName).setCaption("Name");
        grid.addColumn(HotelEntity::getAddress).setCaption("Address");
        grid.addColumn(HotelEntity::getRating).setCaption("Rating");
        grid.addColumn(hotel -> HotelCategoryUtils.getHotelCategoryName(hotel.getHotelCategory())).setCaption("Category");
        
        grid.addColumn(hotel -> LocalDateTime
				.ofInstant(Instant.ofEpochMilli(hotel.getOperatesFrom()), ZoneId.systemDefault()).toLocalDate()).setCaption("Operates from");
        grid.addColumn(HotelEntity::getDescription).setCaption("Description");
        Grid.Column<HotelEntity, String> urlColumn = grid.addColumn(hotel -> "<a href='" + hotel.getUrl() + "' target = '_target'>hotel info</a>", new HtmlRenderer());
        urlColumn.setCaption("Url");
	}

	@Override
	protected void buildFilters() {
		nameFilter.setPlaceholder("filter by name...");
        nameFilter.addValueChangeListener(e -> updateList());
        nameFilter.setValueChangeMode(ValueChangeMode.LAZY);
        
        addressFilter.setPlaceholder("filter by address...");
        addressFilter.addValueChangeListener(e -> updateList());
        addressFilter.setValueChangeMode(ValueChangeMode.LAZY);
	}

	@Override
	protected Collection<Component> getFilterComponents() {
		return Arrays.asList(nameFilter, addressFilter);
	}

	@Override
	protected String getEditButtonCaption() {
		return "Edit hotel";
	}

	@Override
	protected String getDeleteButtonCaption() {
		return "Delete hotel";
	}

	@Override
	protected String getAddButtonCaption() {
		return "Add hotel";
	}

	@Override
	protected Filter createFilter() {
		return new HotelFilter(nameFilter.getValue(), addressFilter.getValue());
	}
	
	@Autowired
    @Override
    public void setService(@Qualifier("hotelService") EntityService<HotelEntity, Filter> service) {
    	super.setService(service);
    }
    
    @Autowired
    @Override
    public void setForm(@Qualifier("hotelForm") AbstractForm<HotelEntity> form) {
    	super.setForm(form);
    }
}
