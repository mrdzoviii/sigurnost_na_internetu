package org.unibl.etf.sni.beans;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WebServiceBean implements Serializable {
	
	private static final long serialVersionUID = -8146937702472013050L;
	private String username;
	private String wsHash;//password+token
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
	public WebServiceBean(String username, String wsHash) {
		super();
		this.username = username;
		this.wsHash = wsHash;
	}
	public WebServiceBean() {
		super();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		result = prime * result + ((wsHash == null) ? 0 : wsHash.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WebServiceBean other = (WebServiceBean) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		if (wsHash == null) {
			if (other.wsHash != null)
				return false;
		} else if (!wsHash.equals(other.wsHash))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "WebServiceBean [username=" + username + ", wsHash=" + wsHash + "]";
	}
	
	
}
