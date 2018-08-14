package org.unibl.etf.sni.clientapp.mysql.dto;

import java.util.Date;

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
}
