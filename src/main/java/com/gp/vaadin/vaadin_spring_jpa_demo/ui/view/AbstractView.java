package com.gp.vaadin.vaadin_spring_jpa_demo.ui.view;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import com.gp.vaadin.vaadin_spring_jpa_demo.model.entity.AbstractEntity;
import com.gp.vaadin.vaadin_spring_jpa_demo.model.filter.Filter;
import com.gp.vaadin.vaadin_spring_jpa_demo.service.EntityService;
import com.gp.vaadin.vaadin_spring_jpa_demo.ui.form.AbstractForm;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public abstract class AbstractView<Entity extends AbstractEntity> extends VerticalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1404336553597902351L;
	
	private Grid<Entity> grid = new Grid<>();

	private AbstractForm<Entity> form;

	private EntityService<Entity, Filter> service;
    
    private Button addBtn = new Button(getAddButtonCaption());
    private Button deleteBtn = new Button(getDeleteButtonCaption());
    private Button editBtn = new Button(getEditButtonCaption());
    
    @PostConstruct
    public void init() {
    	
    	final VerticalLayout layout = new VerticalLayout();
    	
    	buildFilters();

        buildButtons();

        HorizontalLayout toolbar = new HorizontalLayout();
        for (Component filterComponent : getFilterComponents()) {
        	toolbar.addComponent(filterComponent);
        }
        toolbar.addComponents(addBtn, deleteBtn, editBtn);
        
        buildGrid(grid);

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
            Set<Entity> selectedEntities = event.getAllSelectedItems();
            deleteBtn.setEnabled(selectedEntities.size() > 0);
            editBtn.setEnabled(selectedEntities.size() == 1);
        });

    }

	private void buildButtons() {
		addBtn.addClickListener(e -> {
            grid.asMultiSelect().clear();
            form.onActivate(service.createEntity());
        });
        
        deleteBtn.setEnabled(false);
        deleteBtn.addClickListener(e -> {
        	Set<Entity> entitiesToDelete = grid.getSelectedItems();
        	service.delete(entitiesToDelete);
        	editBtn.setEnabled(false);
        	deleteBtn.setEnabled(false);
        	updateList();
        	form.setVisible(false);
        });
        
        editBtn.setEnabled(false);
        editBtn.addClickListener(e -> {
        	Entity entityToEdit = grid.getSelectedItems().iterator().next();
        	editBtn.setEnabled(false);
        	deleteBtn.setEnabled(false);
        	grid.asMultiSelect().clear();
            form.onActivate(entityToEdit);
        });
	}
    
    @Override
    public void enter(ViewChangeEvent event) {
    	onActivate();
    }
    
	public void updateList() {
        List<Entity> entities = service.findAll(createFilter());
        grid.setItems(entities);
    }
    
	public void onCloseEditForm() {
    	deleteBtn.setEnabled(false);
	}
    
    public void onActivate() {
    	form.onInitialize(this);
    	
    	updateList();
    }

    protected abstract void buildGrid(Grid<Entity> grid);
    
    protected abstract void buildFilters();
    
    protected abstract Collection<Component> getFilterComponents();

	protected abstract String getEditButtonCaption();

    protected abstract String getDeleteButtonCaption();

    protected abstract String getAddButtonCaption();
    
    protected abstract Filter createFilter();

    public void setService(EntityService<Entity, Filter> service) {
		this.service = service;
	}
    
    public void setForm(AbstractForm<Entity> form) {
		this.form = form;
	}
    
}
