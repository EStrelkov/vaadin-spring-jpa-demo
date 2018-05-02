package com.gp.vaadin.vaadin_spring_jpa_demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.gp.vaadin.vaadin_spring_jpa_demo.model.entity.HotelEntity;

public interface HotelRepository extends CrudRepository<HotelEntity, Long> {
	
    List<HotelEntity> findByNameContainingAndAddressContaining(String name, String address);

}
