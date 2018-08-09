package org.unibl.etf.sni.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class RestService {
	@Path("/hello")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String test() {
		return "TEST";
	}
}
