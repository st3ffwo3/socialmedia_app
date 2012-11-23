package edu.hm.sisy.ssma.api.object.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.hibernate.validator.constraints.NotEmpty;

import edu.hm.sisy.ssma.api.object.ErrorConstants;

/**
 * Resource für den Benutzer. Es werden alle für die Registrierung benötigten Benutzer-Information in dieser Klasse
 * gehalten.
 * 
 * @author Stefan Wörner
 */
@JsonPropertyOrder( value = { "username", "password", "node-address" }, alphabetic = true )
@Produces( { MediaType.APPLICATION_JSON } )
@Consumes( { MediaType.APPLICATION_JSON } )
public class RegistrationUser extends BasicUser
{

	private static final long serialVersionUID = 9119069304967065381L;

	@NotEmpty( message = ErrorConstants.NODE_ADDRESS_EMPTY_ERROR_MSG )
	private String m_nodeAddress;

	/**
	 * Liefert das Attribut nodeAddress.
	 * 
	 * @return nodeAddress
	 */
	@JsonProperty( "node-address" )
	public String getNodeAddress()
	{
		return m_nodeAddress;
	}

	/**
	 * Setzt das Attribut nodeAddress.
	 * 
	 * @param nodeAddress
	 *            zu setzender Wert für das Attribut nodeAddress
	 */
	@JsonProperty( "node-address" )
	public void setNodeAddress( String nodeAddress )
	{
		m_nodeAddress = nodeAddress;
	}
}
