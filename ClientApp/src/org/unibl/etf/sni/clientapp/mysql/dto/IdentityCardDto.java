package org.unibl.etf.sni.clientapp.mysql.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import org.unibl.etf.sni.clientapp.mysql.dao.UserDao;

@XmlRootElement
public class IdentityCardDto implements Serializable,Document {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7545102414551619060L;
	private Integer id;
	private Date validFrom;
	private Date validUntil;
	private Boolean status;
	private Integer userId;
	private String serial;
	private UserDto user;
	
	public String getStyleClass() {
		return "ic-title";
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
	public IdentityCardDto() {
		super();
	}
	public IdentityCardDto(Integer id, Date validFrom, Date validUntil, Boolean status, Integer userId,UserDto user, String serial) {
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
	@Override
	public String getCategory() {
		return "IDENTITY CARD";
	}
	
	
	
}
