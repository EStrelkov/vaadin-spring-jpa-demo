package com.gp.vaadin.vaadin_spring_jpa_demo.ui;

import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.gp.vaadin.vaadin_spring_jpa_demo.model.entity.HotelCategoryEntity;
import com.gp.vaadin.vaadin_spring_jpa_demo.model.filter.HotelCategoryFilter;
import com.gp.vaadin.vaadin_spring_jpa_demo.service.HotelCategoryService;
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

@SpringView(name = HotelCategoryView.VIEW_NAME)
public class HotelCategoryView extends VerticalLayout implements View {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4743969174479993938L;
	
	public static final String VIEW_NAME = "category";
	
	@Autowired
	private HotelCategoryService hotelCategoryService;
	
    private Grid<HotelCategoryEntity> grid = new Grid<>();
    
    private TextField nameFilter = new TextField();
    
    @Autowired
    private HotelCategoryForm form;
    
    private Button addHotelCategoryBtn = new Button("Add category");
    private Button deleteHotelCategoryBtn = new Button("Delete category");
    private Button editHotelCategoryBtn = new Button("Edit category");
    
    @PostConstruct
    public void init() {
    	
    	final VerticalLayout layout = new VerticalLayout();

        nameFilter.setPlaceholder("filter by name...");
        nameFilter.addValueChangeListener(e -> updateList());
        nameFilter.setValueChangeMode(ValueChangeMode.LAZY);
        
        addHotelCategoryBtn.addClickListener(e -> {
            grid.asMultiSelect().clear();
            form.setHotelCategory(new HotelCategoryEntity());
        });
        
        deleteHotelCategoryBtn.setEnabled(false);
        deleteHotelCategoryBtn.addClickListener(e -> {
        	Set<HotelCategoryEntity> hotelCategoriesToDelete = grid.getSelectedItems();
        	hotelCategoryService.delete(hotelCategoriesToDelete);
        	editHotelCategoryBtn.setEnabled(false);
        	deleteHotelCategoryBtn.setEnabled(false);
        	updateList();
        	form.setVisible(false);
        });
        
        editHotelCategoryBtn.setEnabled(false);
        editHotelCategoryBtn.addClickListener(e -> {
        	HotelCategoryEntity hotelCategoryToEdit = grid.getSelectedItems().iterator().next();
        	editHotelCategoryBtn.setEnabled(false);
        	deleteHotelCategoryBtn.setEnabled(false);
        	grid.asMultiSelect().clear();
            form.setHotelCategory(hotelCategoryToEdit);
        });

        HorizontalLayout toolbar = new HorizontalLayout(nameFilter, addHotelCategoryBtn, deleteHotelCategoryBtn, editHotelCategoryBtn);
        
        grid.addColumn(HotelCategoryEntity::getName).setCaption("Name");

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
            Set<HotelCategoryEntity> selectedHotelCategories = event.getAllSelectedItems();
            deleteHotelCategoryBtn.setEnabled(selectedHotelCategories.size() > 0);
            editHotelCategoryBtn.setEnabled(selectedHotelCategories.size() == 1);
        });

    }
    
    public void updateList() {
        List<HotelCategoryEntity> hotelCategories = hotelCategoryService.findAll(new HotelCategoryFilter(nameFilter.getValue()));
        grid.setItems(hotelCategories);
    }
    
    public void onCloseEditForm() {
    	deleteHotelCategoryBtn.setEnabled(false);
	}
    
    public void onActivate() {
    	form.onInitialize(this);
    	
    	updateList();
    }

    @Override
    public void enter(ViewChangeEvent event) {
    	onActivate();
    }
}
