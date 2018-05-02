package com.gp.vaadin.vaadin_spring_jpa_demo.ui;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.gp.vaadin.vaadin_spring_jpa_demo.model.entity.HotelEntity;
import com.gp.vaadin.vaadin_spring_jpa_demo.model.filter.HotelFilter;
import com.gp.vaadin.vaadin_spring_jpa_demo.service.HotelCategoryService;
import com.gp.vaadin.vaadin_spring_jpa_demo.service.HotelService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.HtmlRenderer;

@SpringView(name = HotelView.VIEW_NAME)
public class HotelView extends VerticalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4471717033494212321L;
	
	public static final String VIEW_NAME = "hotel";
	
	@Autowired
	private HotelService hotelService;
	
	@Autowired
	private HotelCategoryService hotelCategoryService;
	
    private Grid<HotelEntity> grid = new Grid<>();
    
    private TextField nameFilter = new TextField();
    private TextField addressFilter = new TextField();
    
    @Autowired
    private HotelForm form;
    
    private Button addHotelBtn = new Button("Add hotel");
    private Button deleteHotelBtn = new Button("Delete hotel");
    private Button editHotelBtn = new Button("Edit hotel");
    
    @PostConstruct
    public void init() {
    	
    	final VerticalLayout layout = new VerticalLayout();

        nameFilter.setPlaceholder("filter by name...");
        nameFilter.addValueChangeListener(e -> updateList());
        nameFilter.setValueChangeMode(ValueChangeMode.LAZY);
        
        addressFilter.setPlaceholder("filter by address...");
        addressFilter.addValueChangeListener(e -> updateList());
        addressFilter.setValueChangeMode(ValueChangeMode.LAZY);
        
        addHotelBtn.addClickListener(e -> {
            grid.asMultiSelect().clear();
            form.setHotel(new HotelEntity());
        });
        
        deleteHotelBtn.setEnabled(false);
        deleteHotelBtn.addClickListener(e -> {
        	Set<HotelEntity> hotelsToDelete = grid.getSelectedItems();
        	hotelService.delete(hotelsToDelete);
        	editHotelBtn.setEnabled(false);
        	deleteHotelBtn.setEnabled(false);
        	updateList();
        	form.setVisible(false);
        });
        
        editHotelBtn.setEnabled(false);
        editHotelBtn.addClickListener(e -> {
        	HotelEntity hotelToEdit = grid.getSelectedItems().iterator().next();
        	editHotelBtn.setEnabled(false);
        	deleteHotelBtn.setEnabled(false);
        	grid.asMultiSelect().clear();
            form.setHotel(hotelToEdit);
        });

        HorizontalLayout toolbar = new HorizontalLayout(nameFilter, addressFilter, addHotelBtn, deleteHotelBtn, editHotelBtn);
        
        grid.addColumn(HotelEntity::getName).setCaption("Name");
        grid.addColumn(HotelEntity::getAddress).setCaption("Address");
        grid.addColumn(HotelEntity::getRating).setCaption("Rating");
        grid.addColumn(hotel -> hotelCategoryService.getCategoryNameById(hotel.getCategoryId())).setCaption("Category");
        
        grid.addColumn(hotel -> LocalDateTime
				.ofInstant(Instant.ofEpochMilli(hotel.getOperatesFrom()), ZoneId.systemDefault()).toLocalDate()).setCaption("Operates from");
        grid.addColumn(HotelEntity::getDescription).setCaption("Description");
        Grid.Column<HotelEntity, String> urlColumn = grid.addColumn(hotel -> "<a href='" + hotel.getUrl() + "' target = '_target'>hotel info</a>", new HtmlRenderer());
        urlColumn.setCaption("Url");

        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setSizeFull();
        grid.setSizeFull();
        content.setExpandRatio(grid, 1);

        layout.addComponents(toolbar, content);

        updateList();

        addComponent(layout);

        form.setVisible(false);
        
        grid.setSelectionMode(SelectionMode.MULTI);
        
        grid.addSelectionListener(event -> {
            Set<HotelEntity> selectedHotels = event.getAllSelectedItems();
            deleteHotelBtn.setEnabled(selectedHotels.size() > 0);
            editHotelBtn.setEnabled(selectedHotels.size() == 1);
        });
        
    }
    
    public void onActivate() {
    	form.onInitialize(this);
    	
    	updateList();
    }
    
    public void updateList() {
        List<HotelEntity> hotels = hotelService.findAll(new HotelFilter(nameFilter.getValue(), addressFilter.getValue()));
        grid.setItems(hotels);
    }
    
    public void onCloseEditForm() {
		deleteHotelBtn.setEnabled(false);
	}
    
    @Override
    public void enter(ViewChangeEvent event) {
    	onActivate();
    }

}
