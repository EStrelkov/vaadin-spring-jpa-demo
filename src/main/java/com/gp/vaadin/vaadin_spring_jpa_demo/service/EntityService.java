package com.gp.vaadin.vaadin_spring_jpa_demo.service;

import java.util.Collection;
import java.util.List;

import com.gp.vaadin.vaadin_spring_jpa_demo.model.entity.AbstractEntity;
import com.gp.vaadin.vaadin_spring_jpa_demo.model.filter.Filter;

public interface EntityService<Entity extends AbstractEntity, EntityFilter extends Filter> {

	Entity createEntity();

	Entity getById(Long id);
	
	List<Entity> findAll(EntityFilter createFilter);
	
	void save(Entity entity);

	void delete(Collection<Entity> entitiesToDelete);

	void delete(Entity entity);

}
