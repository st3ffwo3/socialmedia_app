package edu.hm.sisy.ssma.api.object.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * Resource für den Benutzer. Es werden alle für die Registrierung benötigten Benutzer-Information in dieser Klasse
 * gehalten.
 * 
 * @author Stefan Wörner
 */
@JsonSerialize( include = Inclusion.NON_NULL )
@Produces( { MediaType.APPLICATION_JSON } )
@Consumes( { MediaType.APPLICATION_JSON } )
public class RegistrationUser extends BaseUser
{

	private static final long serialVersionUID = 9119069304967065381L;

}
