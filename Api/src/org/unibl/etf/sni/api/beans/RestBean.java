package org.unibl.etf.sni.api.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RestBean {
	private WebServiceBean serviceBean;
	private String uid;
	public WebServiceBean getServiceBean() {
		return serviceBean;
	}
	public void setServiceBean(WebServiceBean serviceBean) {
		this.serviceBean = serviceBean;
	}
	
	public RestBean() {
		super();
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public RestBean(WebServiceBean serviceBean, String uid) {
		super();
		this.serviceBean = serviceBean;
		this.uid = uid;
	}
	@Override
	public String toString() {
		return "RestBean [serviceBean=" + serviceBean + ", uid=" + uid + "]";
	}
	
	
	
	
	
	
	
	
}
