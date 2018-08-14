package org.unibl.etf.sni.clientapp.mysql.dto;

import java.util.Date;
import java.util.List;

public interface Document {
	Integer getId();
	Date getValidFrom();
	Date getValidUntil();
	Boolean getStatus();
	Integer getUserId();
	String getSerial();
	UserDto getUser();
	String getCategory();
	String getStyleClass();
	List<DrivingLicenceCategoryDto> getCategories();
}
