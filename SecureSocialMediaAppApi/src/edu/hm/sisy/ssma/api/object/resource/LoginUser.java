package edu.hm.sisy.ssma.api.object.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.ArrayUtils;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

/**
 * Resource für den Benutzer. Es werden alle für die Authentifizierung benötigten Benutzer-Information in dieser Klasse
 * gehalten.
 * 
 * @author Stefan Wörner
 */
@JsonPropertyOrder( value = { "username", "password", "totp-token", "session-token" }, alphabetic = true )
@Produces( { MediaType.APPLICATION_JSON } )
@Consumes( { MediaType.APPLICATION_JSON } )
public class LoginUser extends BasicAuthenticationUser
{

	private static final long serialVersionUID = 9119069304967065381L;

	// @NotEmpty( message = ErrorConstants.USER_TOTP_TOKEN_EMPTY_ERROR_MSG )
	private Long m_totpToken;

	// @NotEmpty( message = ErrorConstants.USER_SESSION_TOKEN_EMPTY_ERROR_MSG )
	private String m_sessionToken;

	/**
	 * Liefert das Attribut totpToken.
	 * 
	 * @return totpToken
	 */
	@JsonProperty( "totp-token" )
	public Long getTotpToken()
	{
		return m_totpToken;
	}

	/**
	 * Setzt das Attribut totpToken.
	 * 
	 * @param totpToken
	 *            zu setzender Wert für das Attribut totpToken
	 */
	@JsonProperty( "totp-token" )
	public void setTotpToken( Long totpToken )
	{
		m_totpToken = totpToken;
	}

	/**
	 * Liefert das Attribut sessionToken.
	 * 
	 * @return sessionToken
	 */
	@JsonProperty( "session-token" )
	public String getSessionToken()
	{
		return m_sessionToken;
	}

	/**
	 * Setzt das Attribut sessionToken.
	 * 
	 * @param sessionToken
	 *            zu setzender Wert für das Attribut sessionToken
	 */
	@JsonProperty( "session-token" )
	public void setSessionToken( String sessionToken )
	{
		m_sessionToken = sessionToken;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.basic.object.AbstractBasicObject#getExclusionList()
	 */
	@Override
	protected String[] getExclusionList()
	{
		return ArrayUtils.addAll( super.getExclusionList(), new String[] { "m_totpToken", "m_sessionToken" } );
	}
}
