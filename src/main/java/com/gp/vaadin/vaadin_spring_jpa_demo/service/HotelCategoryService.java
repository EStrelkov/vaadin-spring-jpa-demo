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
public class HotelCategoryService {
	
	@Autowired
    private HotelCategoryRepository hotelCategoryRepository;
	
	@Transactional(readOnly = true)
	public HotelCategoryEntity getById(Long id) {
		return id == null ? null : hotelCategoryRepository.findOne(id);
	}
	
	@Transactional(readOnly = true)
	public List<HotelCategoryEntity> findAll(HotelCategoryFilter entityFilter) {
		if (entityFilter.isEmpty()) {
			return (List<HotelCategoryEntity>) hotelCategoryRepository.findAll();
		} else {
			return hotelCategoryRepository.findByNameContaining(entityFilter.getName());
		}
	}
	
	@Transactional
	public void delete(Collection<HotelCategoryEntity> entities) {
		for (HotelCategoryEntity entity : entities) {
			delete(entity);
		}
	}
	
	@Transactional
	public void delete(HotelCategoryEntity entity) {
		hotelCategoryRepository.delete(entity);
	}

	@Transactional
	public void save(HotelCategoryEntity entity) {
		hotelCategoryRepository.save(entity);
	}
	
	@Transactional(readOnly = true)
	public String getCategoryNameById(Long id) {
		HotelCategoryEntity hotelCategory = getById(id);
		return hotelCategory == null ? "No Category" : hotelCategory.getName();
	}

}