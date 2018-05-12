package com.gp.vaadin.vaadin_spring_jpa_demo.service;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.util.ReflectionUtils;

import com.gp.vaadin.vaadin_spring_jpa_demo.model.entity.AbstractEntity;
import com.gp.vaadin.vaadin_spring_jpa_demo.model.filter.Filter;

public abstract class AbstractEntityService<Entity extends AbstractEntity, EntityFilter extends Filter>
		implements EntityService<Entity, EntityFilter> {

	protected abstract CrudRepository<Entity, Long> getRepository();

	@Override
	public Entity getById(Long id) {
		return id == null ? null : getRepository().findOne(id);
	}

	public void delete(Collection<Entity> entities) {
		for (Entity entity : entities) {
			delete(entity);
		}
	}

	@Override
	public void delete(Entity entity) {
		getRepository().delete(entity);
	}

	@Override
	public void save(Entity entity) {
		getRepository().save(entity);
	}

	@Override
	public void bulkUpdate(Set<Entity> entitiesToBulkUpdate, String fieldName, Object value) {

		if (fieldName == null || fieldName.isEmpty()) {
			return;
		}

		try {

			for (Entity entity : entitiesToBulkUpdate) {
				Field declaredField = entity.getClass().getDeclaredField(fieldName);
				ReflectionUtils.makeAccessible(declaredField);
				declaredField.set(entity, value);

				save(entity);
			}

		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

}
