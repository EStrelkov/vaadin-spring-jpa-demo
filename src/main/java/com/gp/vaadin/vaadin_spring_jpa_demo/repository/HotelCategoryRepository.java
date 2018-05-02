package com.gp.vaadin.vaadin_spring_jpa_demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.gp.vaadin.vaadin_spring_jpa_demo.model.entity.HotelCategoryEntity;

public interface HotelCategoryRepository extends CrudRepository<HotelCategoryEntity, Long> {
	
    List<HotelCategoryEntity> findByNameContaining(String name);
	
}
