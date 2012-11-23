package edu.hm.sisy.ssma.api.object.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import edu.hm.sisy.ssma.api.object.AbstractRessourceObject;
import edu.hm.sisy.ssma.api.object.ErrorConstants;

/**
 * Basis-Resource für den Benutzer.
 * 
 * @author Stefan Wörner
 */
@JsonPropertyOrder( value = { "username", "password" }, alphabetic = true )
@Produces( { MediaType.APPLICATION_JSON } )
@Consumes( { MediaType.APPLICATION_JSON } )
public class BaseUser extends AbstractRessourceObject
{

	private static final long serialVersionUID = -4266512340620426912L;

	@NotEmpty( message = ErrorConstants.USER_NAME_EMPTY_ERROR_MSG )
	@Email( message = ErrorConstants.USER_NAME_ILLEGAL_EMAIL_ERROR_MSG )
	private String m_username;

	@NotEmpty( message = ErrorConstants.USER_PASSWORD_EMPTY_ERROR_MSG )
	private String m_password;

	/**
	 * Liefert das Attribut username.
	 * 
	 * @return username
	 */
	@JsonProperty( "username" )
	public String getUsername()
	{
		return m_username;
	}

	/**
	 * Setzt das Attribut username.
	 * 
	 * @param username
	 *            zu setzender Wert für das Attribut username
	 */
	@JsonProperty( "username" )
	public void setUsername( String username )
	{
		m_username = username;
	}

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
		return new String[] { "m_password" };
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.basic.object.AbstractBasicObject#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return HashCodeBuilder.reflectionHashCode( INITIAL_NON_ZERO_ODD_NUMBER, MULTIPLIER_NON_ZERO_ODD_NUMBER, this, true,
				BaseUser.class, getExclusionList() );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.basic.object.AbstractBasicObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj )
	{
		return EqualsBuilder.reflectionEquals( this, obj, true, BaseUser.class, getExclusionList() );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		ReflectionToStringBuilder rsb = new ReflectionToStringBuilder( this, ToStringStyle.SHORT_PREFIX_STYLE );
		rsb.setAppendStatics( false );
		rsb.setAppendTransients( true );
		rsb.setUpToClass( BaseUser.class );
		rsb.setExcludeFieldNames( getExclusionList() );
		return rsb.toString();
	}
}
