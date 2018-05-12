package com.gp.vaadin.vaadin_spring_jpa_demo.bulk_update;

import java.util.Map;

import javax.annotation.Resource;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;

@SpringComponent
@ViewScope
public class HotelCategoryBulkUpdateForm extends BulkUpdateForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6354232859216056701L;

	@Resource(name = "hotelCategoryBulkUpdateFieldsConfig")
	@Override
	public void setBulkUpdateFieldsConfig(Map<String, BulkUpdateField> bulkUpdateFieldsConfig) {
		super.setBulkUpdateFieldsConfig(bulkUpdateFieldsConfig);
	}
	
}
