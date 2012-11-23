package edu.hm.sisy.ssma.api.communication.request;

import javax.ejb.Local;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.spi.validation.DoNotValidateRequest;
import org.jboss.resteasy.spi.validation.ValidateRequest;

import edu.hm.sisy.ssma.api.object.resource.BasicUser;
import edu.hm.sisy.ssma.api.object.resource.LoginUser;
import edu.hm.sisy.ssma.api.object.resource.response.UserAuthenticationResponse;

/**
 * REST-Service für die Benutzerauthentifizierung. Verfügbare Aktionen: POST
 * 
 * @author Stefan Wörner
 */
@ValidateRequest
@Local
@Path( "/auth" )
@Produces( { MediaType.APPLICATION_JSON } )
@Consumes( { MediaType.APPLICATION_JSON } )
public interface IAuthenticationService
{

	/**
	 * Authentifiziert und melden einen Benutzer im System an.
	 * 
	 * @param user
	 *            Login Benutzer
	 * @return Session-Token
	 */
	@DoNotValidateRequest
	@POST
	@Path( "login" )
	UserAuthenticationResponse login( LoginUser user );

	/**
	 * Meldet einen Benutzer vom System ab.
	 * 
	 * @param user
	 *            Basic Benutzer
	 */
	@POST
	@Path( "logout" )
	void logout( @Valid BasicUser user );
}
