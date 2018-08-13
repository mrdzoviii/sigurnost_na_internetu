package org.unibl.etf.sni.rest;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;
import org.unibl.etf.sni.beans.AndroidBean;
import org.unibl.etf.sni.beans.DocumentsBean;
import org.unibl.etf.sni.beans.RestBean;
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
	public Response auth(AndroidBean bean) {
		return Response.ok(ServiceUtility.generateAndroidToken(bean)).build();
	}

	@Path("/documents")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getDocumentsByUid(RestBean bean) {
		try {
			if (ServiceUtility.checkServiceBean(bean.getServiceBean()) && bean.getUid() != null) {
				DocumentsBean response = ServiceUtility.getDocumentsByUid(bean.getUid(),
						bean.getServiceBean().getUsername());
				if (response != null) {
					return Response.ok(new JSONObject(response).toString()).build();
				}
				return Response.ok("NOT_AUTHORIZED").build();
			}
		} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
			e.printStackTrace();
		}
		return Response.ok("INVALID_BEAN").build();
	}

}
