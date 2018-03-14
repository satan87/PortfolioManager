package com.nsa.portfoliomanager.web.angularjs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/test2")
public class XJerseyTest {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String produceJSON(  ) {
		
		return "This is a test";

	}
	
}

