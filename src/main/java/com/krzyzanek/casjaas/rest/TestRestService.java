package com.krzyzanek.casjaas.rest;

import com.krzyzanek.casjaas.CustomLoginModule;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

/**
 * Simple test REST service
 *
 * @author Libor Krzyzanek
 */
@Path("/test")
public class TestRestService {

	@Context
	protected SecurityContext securityContext;

	@GET
	@Path("/secure")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed(value = {"user", CustomLoginModule.DEFAULT_ROLE})
	public Response get() {
		Principal principal = securityContext.getUserPrincipal();
		return Response.ok("OK, principal from security context: " + principal.getName()).build();
	}


	@GET
	@Path("/public")
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Response getPublic() {
		return Response.ok("OK PUBLIC").build();
	}

}
