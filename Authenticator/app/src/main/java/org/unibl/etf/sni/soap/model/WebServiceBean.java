package org.unibl.etf.sni.soap.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.ksoap2.serialization.KvmSerializable;

import java.io.Serializable;

public class WebServiceBean implements Serializable{
    @Expose
    @SerializedName("username")
    private String username;
    @Expose
    @SerializedName("wsHash")
    private String wsHash;

    public WebServiceBean(String username, String wsHash) {
        this.username = username;
        this.wsHash = wsHash;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWsHash() {
        return wsHash;
    }

    public void setWsHash(String wsHash) {
        this.wsHash = wsHash;
    }

    public WebServiceBean() {
    }
}
