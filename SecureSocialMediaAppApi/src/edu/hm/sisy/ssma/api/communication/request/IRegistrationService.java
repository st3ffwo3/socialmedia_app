package edu.hm.sisy.ssma.api.communication.request;

import javax.ejb.Local;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.spi.validation.ValidateRequest;

import edu.hm.sisy.ssma.api.object.resource.ReRegistrationUser;
import edu.hm.sisy.ssma.api.object.resource.RegistrationUser;
import edu.hm.sisy.ssma.api.object.resource.response.UserRegistrationResponse;

/**
 * REST-Service für die Benutzerregistrierung. Verfügbare Aktionen: POST
 * 
 * @author Stefan Wörner
 */
@ValidateRequest
@Local
@Path( "/user" )
@Produces( { MediaType.APPLICATION_JSON } )
@Consumes( { MediaType.APPLICATION_JSON } )
public interface IRegistrationService
{

	/**
	 * Erzeugt ein neues Benutzer-Objekt.
	 * 
	 * @param user
	 *            Benutzer
	 * @return QR-Code URL und TOTP Reset Token
	 */
	@POST
	@Path( "register" )
	UserRegistrationResponse register( @Valid RegistrationUser user );

	/**
	 * Nimmt eine erneute Registrierung des Benutzers vor. (Wird benötigt falls Smartphone mit TOTP Generator abhanden
	 * gekommen ist usw.)
	 * 
	 * @param user
	 *            Benutzer
	 * @return Neuer QR-Code URL und neuer TOTP Reset Token
	 */
	@POST
	@Path( "re-register" )
	UserRegistrationResponse reRegister( @Valid ReRegistrationUser user );
}
