package com.gp.vaadin.vaadin_spring_jpa_demo.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gp.vaadin.vaadin_spring_jpa_demo.model.entity.HotelCategoryEntity;
import com.gp.vaadin.vaadin_spring_jpa_demo.model.filter.HotelCategoryFilter;
import com.gp.vaadin.vaadin_spring_jpa_demo.repository.HotelCategoryRepository;

@Service
public class HotelCategoryService implements EntityService<HotelCategoryEntity, HotelCategoryFilter> {
	
	@Autowired
    private HotelCategoryRepository hotelCategoryRepository;
	
	@Override
	public HotelCategoryEntity createEntity() {
		return new HotelCategoryEntity();
	}
	
	@Override
	@Transactional(readOnly = true)
	public HotelCategoryEntity getById(Long id) {
		return id == null ? null : hotelCategoryRepository.findOne(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<HotelCategoryEntity> findAll(HotelCategoryFilter entityFilter) {
		if (entityFilter.isEmpty()) {
			return (List<HotelCategoryEntity>) hotelCategoryRepository.findAll();
		} else {
			return hotelCategoryRepository.findByNameContaining(entityFilter.getName());
		}
	}
	
	@Override
	@Transactional
	public void delete(Collection<HotelCategoryEntity> entities) {
		for (HotelCategoryEntity entity : entities) {
			delete(entity);
		}
	}
	
	@Override
	@Transactional
	public void delete(HotelCategoryEntity entity) {
		hotelCategoryRepository.delete(entity);
	}

	@Override
	@Transactional
	public void save(HotelCategoryEntity entity) {
		hotelCategoryRepository.save(entity);
	}

}
