package com.gp.vaadin.vaadin_spring_jpa_demo.ui.form;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.gp.vaadin.vaadin_spring_jpa_demo.model.entity.HotelCategoryEntity;
import com.gp.vaadin.vaadin_spring_jpa_demo.model.entity.HotelEntity;
import com.gp.vaadin.vaadin_spring_jpa_demo.model.filter.Filter;
import com.gp.vaadin.vaadin_spring_jpa_demo.model.filter.HotelCategoryFilter;
import com.gp.vaadin.vaadin_spring_jpa_demo.service.EntityService;
import com.gp.vaadin.vaadin_spring_jpa_demo.ui.view.AbstractView;
import com.gp.vaadin.vaadin_spring_jpa_demo.util.DateUtils;
import com.gp.vaadin.vaadin_spring_jpa_demo.util.HotelCategoryUtils;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

@SpringComponent
@ViewScope
public class HotelForm extends AbstractForm<HotelEntity> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4404488930218649725L;

	private TextField name = new TextField("Name");
	private TextField address = new TextField("Address");
	private TextField rating = new TextField("Rating");
	private DateField operatesFrom = new DateField("Operates from");
	private ComboBox<HotelCategoryEntity> category = new ComboBox<>("Category");
	private TextField url = new TextField("Url");
	private TextArea description = new TextArea("Description");

	@Autowired
	@Qualifier("hotelCategoryService")
	private EntityService<HotelCategoryEntity, HotelCategoryFilter> categoryService;

	@Override
	public void onInitialize(AbstractView<HotelEntity> view) {
		super.onInitialize(view);
		
		category.setItems(categoryService.findAll(new HotelCategoryFilter()));
	}
	
	@Override
	protected void addAndPrepareFormComponents() {
		addComponents(name, address, rating, category, operatesFrom, url, description);

		binder.forField(name).asRequired("Please enter a name").bind(HotelEntity::getName, HotelEntity::setName);
		name.setDescription("Hotel name");

		binder.forField(address).asRequired("Please enter a address").withValidator(new HotelAddressValidator())
				.bind(HotelEntity::getAddress, HotelEntity::setAddress);
		address.setDescription("Hotel address");

		binder.forField(rating).asRequired("Please enter a rating")
				.withConverter(new StringToIntegerConverter("Must be a number"))
				.withValidator(rating -> rating >= 0 && rating <= 5, "Rating should be between 0 and 5")
				.bind(HotelEntity::getRating, HotelEntity::setRating);
		rating.setDescription("Hotel rating");

		binder.forField(operatesFrom).asRequired("Please enter a operates from date")
				.withValidator(date -> date.isBefore(LocalDate.now()), "Operates from date should be in past").bind(
						hotel -> hotel.getOperatesFrom() == null ? null
								: DateUtils.getLocalDate(hotel.getOperatesFrom()),
						(hotel, localDate) -> hotel.setOperatesFrom(DateUtils.getTime(localDate)));
		operatesFrom.setDescription("Hotel operates from date");

		binder.forField(category).withNullRepresentation(HotelCategoryUtils.createEmptyHotelCategory())
				.asRequired("Please enter a category")
				.bind(HotelEntity::getHotelCategory, HotelEntity::setHotelCategory);
		category.setDescription("Hotel category");

		binder.forField(url).asRequired("Please enter a url").bind(HotelEntity::getUrl, HotelEntity::setUrl);
		url.setDescription("Hotel url");

		binder.forField(description).bind(HotelEntity::getDescription, HotelEntity::setDescription);
		description.setDescription("Hotel description");
	}

	@Override
	protected Class<HotelEntity> getEntityClass() {
		return HotelEntity.class;
	}
	
	@Autowired
	@Override
	public void setService(@Qualifier("hotelService") EntityService<HotelEntity, Filter> service) {
		super.setService(service);
	}

}
