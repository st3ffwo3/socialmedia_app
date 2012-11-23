package edu.hm.sisy.ssma.api.communication.request;

import javax.ejb.Local;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.spi.validation.ValidateRequest;

import edu.hm.sisy.ssma.api.object.resource.AuthenticationUser;

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
	 * Erzeugt ein neues Benutzer-Objekt.
	 * 
	 * @param user
	 *            Benutzer
	 * @return QR-Code URL
	 */
	@POST
	@Path( "" )
	String authenticate( @Valid AuthenticationUser user );
}
