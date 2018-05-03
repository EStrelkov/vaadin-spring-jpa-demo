package com.gp.vaadin.vaadin_spring_jpa_demo.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "HOTEL")
public class HotelEntity extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5028515958056807912L;

	@Column(name = "NAME")
	private String name;

	@Column(name = "ADDRESS", nullable = false)
	private String address;

	@Column(name = "RATING", nullable = false)
	private Integer rating = 0;

	@Column(name = "OPERATES_FROM", nullable = false)
	private Long operatesFrom;

	@ManyToOne
    @JoinColumn(name = "CATEGORY")
	@NotFound(action = NotFoundAction.IGNORE)
	private HotelCategoryEntity hotelCategory;
	
	@Column(name = "URL", nullable = false)
	private String url;
	
	@Column(name = "DESCRIPTION")
	private String description;

	@Override
	public String toString() {
		return name + " " + rating +"stars " + address;
	}

	@Override
	public HotelEntity clone() {
		try {
			return (HotelEntity) super.clone();
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public Long getOperatesFrom() {
		return operatesFrom;
	}

	public void setOperatesFrom(Long operatesFrom) {
		this.operatesFrom = operatesFrom;
	}

	public HotelCategoryEntity getHotelCategory() {
		return hotelCategory;
	}

	public void setHotelCategory(HotelCategoryEntity hotelCategory) {
		this.hotelCategory = hotelCategory;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}