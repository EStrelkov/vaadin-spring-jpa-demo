package com.gp.vaadin.vaadin_spring_jpa_demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.gp.vaadin.vaadin_spring_jpa_demo.model.entity.HotelCategoryEntity;
import com.gp.vaadin.vaadin_spring_jpa_demo.model.filter.HotelCategoryFilter;
import com.gp.vaadin.vaadin_spring_jpa_demo.repository.HotelCategoryRepository;

@Service
public class HotelCategoryService extends AbstractEntityService<HotelCategoryEntity, HotelCategoryFilter> {
	
	@Autowired
    private HotelCategoryRepository hotelCategoryRepository;
	
	@Override
	protected CrudRepository<HotelCategoryEntity, Long> getRepository() {
		return hotelCategoryRepository;
	}
	
	@Override
	public HotelCategoryEntity createEntity() {
		return new HotelCategoryEntity();
	}
	
	@Override
	public List<HotelCategoryEntity> findAll(HotelCategoryFilter entityFilter) {
		if (entityFilter.isEmpty()) {
			return (List<HotelCategoryEntity>) hotelCategoryRepository.findAll();
		} else {
			return hotelCategoryRepository.findByNameContaining(entityFilter.getName());
		}
	}
	
}
