package com.gp.vaadin.vaadin_spring_jpa_demo.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gp.vaadin.vaadin_spring_jpa_demo.model.entity.HotelEntity;
import com.gp.vaadin.vaadin_spring_jpa_demo.model.filter.HotelFilter;
import com.gp.vaadin.vaadin_spring_jpa_demo.repository.HotelRepository;

@Service
public class HotelService implements EntityService<HotelEntity, HotelFilter> {
	
	@Autowired
    private HotelRepository hotelRepository;
	
	@Override
	public HotelEntity createEntity() {
		return new HotelEntity();
	}
	
	@Override
	@Transactional(readOnly = true)
	public HotelEntity getById(Long id) {
		return id == null ? null : hotelRepository.findOne(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<HotelEntity> findAll(HotelFilter entityFilter) {
		if (entityFilter.isEmpty()) {
			return (List<HotelEntity>) hotelRepository.findAll();
		} else {
			return hotelRepository.findByNameContainingAndAddressContaining(entityFilter.getName(), entityFilter.getAddress());
		}
	}
	
	@Override
	@Transactional
	public void delete(Collection<HotelEntity> entities) {
		for (HotelEntity entity : entities) {
			delete(entity);
		}
	}
	
	@Override
	@Transactional
	public void delete(HotelEntity entity) {
		hotelRepository.delete(entity);
	}

	@Override
	@Transactional
	public void save(HotelEntity entity) {
		hotelRepository.save(entity);
	}

}
