package org.unibl.etf.sni.bean;

import java.io.Serializable;
import java.util.Date;

public class SessionBean implements Serializable{
    private String username;
    private String password;
    private String token;
    private Date validUntil;

    public SessionBean(String username, String password, String token, Date validUntil) {
        this.username = username;
        this.password = password;
        this.token = token;
        this.validUntil = validUntil;
    }

    public SessionBean() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Date validUntil) {
        this.validUntil = validUntil;
    }
}
