package com.gp.vaadin.vaadin_spring_jpa_demo.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import com.gp.vaadin.vaadin_spring_jpa_demo.model.entity.AbstractEntity;
import com.gp.vaadin.vaadin_spring_jpa_demo.model.filter.Filter;

public interface EntityService<Entity extends AbstractEntity, EntityFilter extends Filter> {

	Entity createEntity();

	@Transactional(readOnly = true, rollbackFor = Throwable.class)
	Entity getById(Long id);
	
	@Transactional(readOnly = true, rollbackFor = Throwable.class)
	List<Entity> findAll(EntityFilter createFilter);
	
	@Transactional(rollbackFor = Throwable.class)
	void save(Entity entity);

	@Transactional(rollbackFor = Throwable.class)
	void delete(Collection<Entity> entitiesToDelete);

	@Transactional(rollbackFor = Throwable.class)
	void delete(Entity entity);
	
	@Transactional(rollbackFor = Throwable.class)
	void bulkUpdate(Set<Entity> entitiesToBulkUpdate, String fieldName, Object value);

}
