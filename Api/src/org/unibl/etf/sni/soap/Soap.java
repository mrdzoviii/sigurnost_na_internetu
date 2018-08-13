package org.unibl.etf.sni.soap;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.text.ParseException;
import java.util.Date;

import org.json.JSONObject;
import org.unibl.etf.sni.beans.WebServiceBean;
import org.unibl.etf.sni.util.ServiceUtility;

public class Soap {
	public String getDocumentsByDate(String username, String wsHash, String time) {
		WebServiceBean bean = new WebServiceBean(username, wsHash);
		Date date;
		try {
			date = ServiceUtility.sdf.parse(time);

			try {
				if (ServiceUtility.checkServiceBean(bean)) {
					if (ServiceUtility.isAdmin(bean.getUsername())) {
						return new JSONObject(ServiceUtility.getDocumentsByDate(date)).toString();
					}
					return "NOT_AUTHORIZED";
				}
			} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
				e.printStackTrace();
			}
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return "INVALID_BEAN";
	}
}
