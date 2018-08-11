package org.unibl.etf.sni.soap;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Date;

import org.json.JSONObject;
import org.unibl.etf.sni.beans.WebServiceBean;
import org.unibl.etf.sni.util.ServiceUtility;

public class Soap {
	public String getDocumentsByDate(String username,String wsHash,long time) {
		WebServiceBean bean=new WebServiceBean(username,wsHash);
		Date date=new Date(time);
		try {
			if(ServiceUtility.checkServiceBean(bean)) {
				return new JSONObject(ServiceUtility.getDocumentsByDate(date)).toString();
			}
		} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
			e.printStackTrace();
		}
		return "INVALID_BEAN";
	}
}
