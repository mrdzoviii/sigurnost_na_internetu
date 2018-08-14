package org.unibl.etf.sni.clientapp.mysql.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlTransient;

import org.unibl.etf.sni.clientapp.mysql.dao.CategoryDao;

@XmlTransient
public class DrivingLicenceCategoryDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2440818480767615328L;
	@XmlTransient
	private Integer drivingLicenceId;
	@XmlTransient
	private Integer categoryId;
	private Date validFrom;
	private Boolean banned;
	@XmlTransient
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
		if(category==null) {
			category=CategoryDao.getById(categoryId);
		}
		return category;
	}



	public void setCategory(CategoryDto category) {
		this.category = category;
	}
	
	
	
	
	
	
	
	
	
}
