package edu.hm.sisy.ssma.api.object.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.ArrayUtils;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.hibernate.validator.constraints.NotEmpty;

import edu.hm.sisy.ssma.api.object.ErrorConstants;

/**
 * Basis-Resource für den Auth-Benutzer.
 * 
 * @author Stefan Wörner
 */
@JsonPropertyOrder( value = { "username", "password" }, alphabetic = true )
// @Produces( { MediaType.APPLICATION_JSON } )
@Consumes( { MediaType.APPLICATION_JSON } )
public class BasicAuthUser extends BasicUser
{

	private static final long serialVersionUID = 5447071548185842572L;

	@NotEmpty( message = ErrorConstants.USER_PASSWORD_EMPTY_ERROR_MSG )
	private String m_password;

	/**
	 * Liefert das Attribut password.
	 * 
	 * @return password
	 */
	@JsonProperty( "password" )
	public String getPassword()
	{
		return m_password;
	}

	/**
	 * Setzt das Attribut password.
	 * 
	 * @param password
	 *            zu setzender Wert für das Attribut password
	 */
	@JsonProperty( "password" )
	public void setPassword( String password )
	{
		m_password = password;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.basic.object.AbstractBasicObject#getExclusionList()
	 */
	@Override
	protected String[] getExclusionList()
	{
		return ArrayUtils.addAll( super.getExclusionList(), new String[] { "m_password" } );
	}
}
