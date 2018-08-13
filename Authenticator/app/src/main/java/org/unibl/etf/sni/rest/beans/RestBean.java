package org.unibl.etf.sni.rest.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.unibl.etf.sni.soap.model.WebServiceBean;

public class RestBean {
    @Expose
    @SerializedName("serviceBean")
    private WebServiceBean serviceBean;
    @Expose
    @SerializedName("uid")
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
}
