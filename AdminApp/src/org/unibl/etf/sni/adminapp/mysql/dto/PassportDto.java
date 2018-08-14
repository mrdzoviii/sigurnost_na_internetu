package org.unibl.etf.sni.adminapp.mysql.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.unibl.etf.sni.adminapp.mysql.dao.UserDao;

@XmlRootElement
public class PassportDto {
	@XmlTransient
	private Integer id;
	private Date validFrom;
	private Date validUntil;
	private Boolean status;
	private Integer userId;
	private String serial;
	@XmlTransient
	private UserDto user;
	
	
	
	public UserDto getUser() {
		if(user==null) {
			user=UserDao.getById(userId);
		}
		return user;
	}
	public void setUser(UserDto user) {
		this.user = user;
	}
	public PassportDto() {
		super();
	}
	public PassportDto(Integer id, Date validFrom, Date validUntil, Boolean status, Integer userId,UserDto user, String serial) {
		super();
		this.id = id;
		this.validFrom = validFrom;
		this.validUntil = validUntil;
		this.status = status;
		this.userId = userId;
		this.user=user;
		this.serial = serial;
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
