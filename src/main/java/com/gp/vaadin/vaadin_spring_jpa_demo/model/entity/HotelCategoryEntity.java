package com.gp.vaadin.vaadin_spring_jpa_demo.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "HOTEL_CATEGORY")
public class HotelCategoryEntity extends AbstractEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1995198874883280710L;


	@Column(name = "NAME")
	private String name;

	@Override
	public String toString() {
		return name;
	}

	@Override
	public HotelCategoryEntity clone() {
		try {
			return (HotelCategoryEntity) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static HotelCategoryEntity empty() {
		HotelCategoryEntity hotelCategory = new HotelCategoryEntity();
		hotelCategory.setName("No Category");
		return hotelCategory;
	}
}
