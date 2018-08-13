package org.unibl.etf.sni.model;

import java.util.Date;

public class DrivingLicenceCategoryDto {
	private Integer drivingLicenceId;
	private Integer categoryId;
	private Date validFrom;
	private Boolean banned;
	private CategoryDto category;
	
	
	
	public DrivingLicenceCategoryDto() {
		super();
	}



	public DrivingLicenceCategoryDto(Integer drivingLicenceId, Integer categoryId, Date validFrom, Boolean banned,
			CategoryDto category) {
		super();
		this.drivingLicenceId = drivingLicenceId;
		this.categoryId = categoryId;
		this.validFrom = validFrom;
		this.banned = banned;
		this.category = category;
	}



	public Integer getDrivingLicenceId() {
		return drivingLicenceId;
	}



	public void setDrivingLicenceId(Integer drivingLicenceId) {
		this.drivingLicenceId = drivingLicenceId;
	}



	public Integer getCategoryId() {
		return categoryId;
	}



	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}



	public Date getValidFrom() {
		return validFrom;
	}



	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}



	public Boolean getBanned() {
		return banned;
	}



	public void setBanned(Boolean banned) {
		this.banned = banned;
	}



	public CategoryDto getCategory() {
		return category;
	}



	public void setCategory(CategoryDto category) {
		this.category = category;
	}
	
	
	
	
	
	
	
	
	
}
