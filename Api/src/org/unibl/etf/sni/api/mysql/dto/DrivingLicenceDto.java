package org.unibl.etf.sni.api.mysql.dto;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.unibl.etf.sni.api.mysql.dao.UserDao;

@XmlRootElement
public class DrivingLicenceDto {
	@XmlTransient
	private Integer id;
	private Date validFrom;
	private Date validUntil;
	private Boolean status;
	private Integer userId;
	private String serial;
	@XmlTransient
	private UserDto user;
	private List<DrivingLicenceCategoryDto> categories;
	
	
	
	
	
	public List<DrivingLicenceCategoryDto> getCategories() {
		return categories;
	}
	public void setCategories(List<DrivingLicenceCategoryDto> categories) {
		this.categories = categories;
	}
	public UserDto getUser() {
		if(user==null) {
			user=UserDao.getById(userId);
		}
		return user;
	}
	public void setUser(UserDto user) {
		this.user = user;
	}
	public DrivingLicenceDto() {
		super();
	}
	
	public DrivingLicenceDto(Integer id, Date validFrom, Date validUntil, Boolean status, Integer userId, String serial,
			UserDto user, List<DrivingLicenceCategoryDto> categories) {
		super();
		this.id = id;
		this.validFrom = validFrom;
		this.validUntil = validUntil;
		this.status = status;
		this.userId = userId;
		this.serial = serial;
		this.user = user;
		this.categories = categories;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getValidFrom() {
		return validFrom;
	}
	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}
	public Date getValidUntil() {
		return validUntil;
	}
	public void setValidUntil(Date validUntil) {
		this.validUntil = validUntil;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	
	
}
