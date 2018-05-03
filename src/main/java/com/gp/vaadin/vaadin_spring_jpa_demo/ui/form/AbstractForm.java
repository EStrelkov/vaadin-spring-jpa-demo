package com.gp.vaadin.vaadin_spring_jpa_demo.ui.form;

import javax.annotation.PostConstruct;

import com.gp.vaadin.vaadin_spring_jpa_demo.model.entity.AbstractEntity;
import com.gp.vaadin.vaadin_spring_jpa_demo.model.filter.Filter;
import com.gp.vaadin.vaadin_spring_jpa_demo.service.EntityService;
import com.gp.vaadin.vaadin_spring_jpa_demo.ui.view.AbstractView;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;

public abstract class AbstractForm<Entity extends AbstractEntity> extends FormLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7080631486457534544L;

	private Button save = new Button("Save");
	private Button close = new Button("Close");

	protected Binder<Entity> binder = new Binder<>(getEntityClass());

	private Entity entity;

	private AbstractView<Entity> view;

	private EntityService<Entity, Filter> service;

	@PostConstruct
	public void init() {

		setSizeUndefined();
		HorizontalLayout buttons = new HorizontalLayout(save, close);

		addAndPrepareFormComponents();

		addComponents(buttons);

		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(KeyCode.ENTER);

		save.addClickListener(e -> this.save());
		close.addClickListener(e -> this.close());
	}

	public void onInitialize(AbstractView<Entity> view) {
		this.view = view;
	}

	public void onActivate(Entity entity) {
		this.entity = entity;
		binder.readBean(entity);
		setVisible(true);
	}

	private void save() {

		if (binder.isValid()) {
			try {
				binder.writeBean(entity);
				service.save(entity);
			} catch (ValidationException e) {
				Notification.show("Unable to save." + e.getMessage(), Type.HUMANIZED_MESSAGE);
			} finally {
				close();
			}
		} else {
			Notification.show("Unable to save.", Type.HUMANIZED_MESSAGE);
		}

	}

	private void close() {
		view.updateList();
		setVisible(false);
		view.onCloseEditForm();
	}

	protected abstract void addAndPrepareFormComponents();

	protected abstract Class<Entity> getEntityClass();

	public void setService(EntityService<Entity, Filter> service) {
		this.service = service;
	}
}
