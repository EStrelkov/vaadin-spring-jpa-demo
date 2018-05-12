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
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((hotelCategory == null) ? 0 : hotelCategory.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((operatesFrom == null) ? 0 : operatesFrom.hashCode());
		result = prime * result + ((rating == null) ? 0 : rating.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		HotelEntity other = (HotelEntity) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (hotelCategory == null) {
			if (other.hotelCategory != null)
				return false;
		} else if (!hotelCategory.equals(other.hotelCategory))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (operatesFrom == null) {
			if (other.operatesFrom != null)
				return false;
		} else if (!operatesFrom.equals(other.operatesFrom))
			return false;
		if (rating == null) {
			if (other.rating != null)
				return false;
		} else if (!rating.equals(other.rating))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return name + " " + rating +"stars " + address;
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