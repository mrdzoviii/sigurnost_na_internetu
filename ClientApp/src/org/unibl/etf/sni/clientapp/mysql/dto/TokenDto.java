package org.unibl.etf.sni.clientapp.mysql.dto;

import java.io.Serializable;
import java.util.Date;

import org.unibl.etf.sni.clientapp.mysql.dao.UserDao;

public class TokenDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1293899053015219740L;
	private Integer id;
	private Integer userId;
	private UserDto user;
	private Date validUntil;
	private String token;
	private Boolean sso;
	
	
	public Boolean getSso() {
		return sso;
	}

	public void setSso(Boolean sso) {
		this.sso = sso;
	}

	public TokenDto() {
		super();
	}
	
	public TokenDto(Integer id, Integer userId, UserDto user, Date validUntil,String token,Boolean sso) {
		super();
		this.id = id;
		this.userId = userId;
		this.user = user;
		this.validUntil = validUntil;
		this.token=token;
		this.sso=sso;
	}
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
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
	public Date getValidUntil() {
		return validUntil;
	}
	public void setValidUntil(Date createTime) {
		this.validUntil = createTime;
	}
	
	
	
}
