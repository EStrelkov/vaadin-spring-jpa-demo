package com.gp.vaadin.vaadin_spring_jpa_demo.model.filter;

public class HotelCategoryFilter implements Filter {

	private String name;

	public HotelCategoryFilter(String name) {
		super();
		this.name = name;
	}
	
	public HotelCategoryFilter() {
		super();
	}

	public String getName() {
		return name;
	}
	
	public boolean isEmpty() {
		return name == null;
	}
	
}
