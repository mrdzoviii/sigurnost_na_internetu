package org.unibl.etf.sni.model;

import java.util.Date;

public interface Document {
    Date getValidFrom();
    Date getValidUntil();
    Boolean getStatus();
    Integer getUserId();
    String getSerial();
    UserDto getUser();
}
