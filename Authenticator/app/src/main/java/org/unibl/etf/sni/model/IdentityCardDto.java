package org.unibl.etf.sni.model;

import java.util.Date;



public class IdentityCardDto implements Document {
	
	private Integer id;
	private Date validFrom;
	private Date validUntil;
	private Boolean status;
	private Integer userId;
	private String serial;
	private UserDto user;
	
	
	public UserDto getUser() {
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

	public Date getValidFrom() {
		return validFrom;
	}

	public Date getValidUntil() {
		return validUntil;
	}

	public Boolean getStatus() {
		return status;
	}

	public Integer getUserId() {
		return userId;
	}

	public String getSerial() {
		return serial;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public void setValidUntil(Date validUntil) {
		this.validUntil = validUntil;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}
}
