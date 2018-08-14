package org.unibl.etf.sni.clientapp.mysql.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
public class CategoryDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2560456122394660201L;
	@XmlTransient
	private Integer id;
	private String category;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public CategoryDto(Integer id, String category) {
		super();
		this.id = id;
		this.category = category;
	}
	public CategoryDto() {
		super();
	}
	
	
	
	
}
