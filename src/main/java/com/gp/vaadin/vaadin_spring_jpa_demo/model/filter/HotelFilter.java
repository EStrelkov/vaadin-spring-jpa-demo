package com.gp.vaadin.vaadin_spring_jpa_demo.model.filter;

public class HotelFilter implements Filter {

	private final String name;
	
	private final String address;

	public HotelFilter(String name, String address) {
		super();
		this.name = name;
		this.address = address;
	}
	
	public String getName() {
		return name;
	}
	
	public String getAddress() {
		return address;
	}
	
	public boolean isEmpty() {
		return name == null && address == null;
	}
}
