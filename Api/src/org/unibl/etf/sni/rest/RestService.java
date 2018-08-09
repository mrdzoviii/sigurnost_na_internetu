package org.unibl.etf.sni.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.unibl.etf.sni.beans.AndroidBean;
import org.unibl.etf.sni.util.ServiceUtility;

@Path("/")
public class RestService {
	@Path("/hello")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String test() {
		return "TEST";
	}
	
	
	@Path("/android/auth")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String auth(AndroidBean bean) {
		return ServiceUtility.generateAndroidToken(bean);
	}
	
}
