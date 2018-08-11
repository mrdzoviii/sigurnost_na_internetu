package org.unibl.etf.sni.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RestBean {
	private WebServiceBean serviceBean;
	private String username;
	public WebServiceBean getServiceBean() {
		return serviceBean;
	}
	public void setServiceBean(WebServiceBean serviceBean) {
		this.serviceBean = serviceBean;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public RestBean(WebServiceBean serviceBean, String username) {
		super();
		this.serviceBean = serviceBean;
		this.username = username;
	}
	public RestBean() {
		super();
	}
	@Override
	public String toString() {
		return "RestBean [serviceBean=" + serviceBean + ", username=" + username + "]";
	}
	
	
	
	
}
