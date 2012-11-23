package edu.hm.sisy.ssma.api.communication.request;

import javax.ejb.Local;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.spi.validation.ValidateRequest;

import edu.hm.sisy.ssma.api.object.resource.LoginUser;

/**
 * REST-Service für die Benutzerauthentifizierung. Verfügbare Aktionen: POST
 * 
 * @author Stefan Wörner
 */
@ValidateRequest
@Local
@Path( "/user" )
@Produces( { MediaType.APPLICATION_JSON } )
@Consumes( { MediaType.APPLICATION_JSON } )
public interface IAuthenticationService
{

	/**
	 * Authentifiziert und melden einen Benutzer im System an.
	 * 
	 * @param user
	 *            Login Benutzer
	 * @param ssmsToken
	 *            Base64 codiertes SSMS-Token (Bestehend aus Nutzername und SessionToken)
	 * @param response
	 *            HttpServletResponse (wird verwendet um in den Response Header zu schreiben)
	 */
	@POST
	@Path( "login" )
	void login( @Valid LoginUser user, @HeaderParam( "ssms-token" ) String ssmsToken, @Context HttpServletResponse response );

	/**
	 * Meldet einen Benutzer vom System ab.
	 * 
	 * @param ssmsToken
	 *            Base64 codiertes SSMS-Token (Bestehend aus Nutzername und SessionToken)
	 */
	@POST
	@Path( "logout" )
	void logout( @HeaderParam( "ssms-token" ) String ssmsToken );
}
