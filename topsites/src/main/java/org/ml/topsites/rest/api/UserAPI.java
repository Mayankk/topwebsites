package org.ml.topsites.rest.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Class for handling the rest calls for fetching user related data
 * @author mkurra
 *
 */

@Path("/user")
public class UserAPI {
	private static Logger logger =
				LogManager.getLogger(UserAPI.class);

	@GET
	@Path("login")
	@Produces( { MediaType.APPLICATION_JSON })
	public Response validate(){
		logger.info("user authenticated");
		return Response.ok(new Message("success")).build();
	}
}
