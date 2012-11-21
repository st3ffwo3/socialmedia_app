package edu.hm.sisy.ssma.api.communication.request;

import javax.ejb.Local;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.hm.sisy.ssma.api.object.resource.RegistrationUser;

/**
 * REST-Service für die Benutzerregistrierung. Verfügbare Aktionen: POST
 * 
 * @author Stefan Wörner
 */
@Local
@Path( "/register" )
@Produces( { MediaType.APPLICATION_JSON } )
@Consumes( { MediaType.APPLICATION_JSON } )
public interface IRegistrationService
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
	String register( RegistrationUser user );
}
