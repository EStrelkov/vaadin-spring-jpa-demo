package com.gp.vaadin.vaadin_spring_jpa_demo.util;

import com.gp.vaadin.vaadin_spring_jpa_demo.model.entity.HotelCategoryEntity;

public class HotelCategoryUtils {

	private static final String NO_CATEGORY = "No Category";

	public static HotelCategoryEntity createEmptyHotelCategory() {
		HotelCategoryEntity hotelCategory = new HotelCategoryEntity();
		hotelCategory.setName(NO_CATEGORY);
		return hotelCategory;
	}

	public static String getHotelCategoryName(HotelCategoryEntity hotelCategory) {
		return hotelCategory == null ? NO_CATEGORY : hotelCategory.getName();
	}
	
}
