package edu.hm.sisy.ssma.api.object.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.ArrayUtils;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.hibernate.validator.constraints.NotEmpty;

import edu.hm.sisy.ssma.api.object.ErrorConstants;

/**
 * Resource für den Benutzer. Es werden alle für die Re-Registrierung benötigten Benutzer-Information in dieser Klasse
 * gehalten.
 * 
 * @author Stefan Wörner
 */
@JsonPropertyOrder( value = { "username", "password", "reset-token" }, alphabetic = true )
// @Produces( { MediaType.APPLICATION_JSON } )
@Consumes( { MediaType.APPLICATION_JSON } )
public class ReRegistrationUser extends BasicAuthUser
{

	private static final long serialVersionUID = -7421164686759398779L;

	@NotEmpty( message = ErrorConstants.RESET_TOKEN_EMPTY_ERROR_MSG )
	private String m_resetToken;

	/**
	 * Liefert das Attribut resetTtoken.
	 * 
	 * @return resetTtoken
	 */
	@JsonProperty( "reset-token" )
	public String getResetToken()
	{
		return m_resetToken;
	}

	/**
	 * Setzt das Attribut resetTtoken.
	 * 
	 * @param resetToken
	 *            zu setzender Wert für das Attribut resetTtoken
	 */
	@JsonProperty( "reset-token" )
	public void setResetToken( String resetToken )
	{
		m_resetToken = resetToken;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.basic.object.AbstractBasicObject#getExclusionList()
	 */
	@Override
	protected String[] getExclusionList()
	{
		return ArrayUtils.addAll( super.getExclusionList(), new String[] { "m_resetToken" } );
	}
}
