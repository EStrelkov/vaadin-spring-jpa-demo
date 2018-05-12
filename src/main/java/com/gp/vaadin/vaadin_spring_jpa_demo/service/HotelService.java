package com.gp.vaadin.vaadin_spring_jpa_demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.gp.vaadin.vaadin_spring_jpa_demo.model.entity.HotelEntity;
import com.gp.vaadin.vaadin_spring_jpa_demo.model.filter.HotelFilter;
import com.gp.vaadin.vaadin_spring_jpa_demo.repository.HotelRepository;

@Service
public class HotelService extends AbstractEntityService<HotelEntity, HotelFilter> {
	
	@Autowired
    private HotelRepository hotelRepository;
	
	@Override
	protected CrudRepository<HotelEntity, Long> getRepository() {
		return hotelRepository;
	}
	
	@Override
	public HotelEntity createEntity() {
		return new HotelEntity();
	}
	
	@Override
	public List<HotelEntity> findAll(HotelFilter entityFilter) {
		if (entityFilter.isEmpty()) {
			return (List<HotelEntity>) hotelRepository.findAll();
		} else {
			return hotelRepository.findByNameContainingAndAddressContaining(entityFilter.getName(), entityFilter.getAddress());
		}
	}
	
}
