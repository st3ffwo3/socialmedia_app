package edu.hm.sisy.ssma.api.object.resource.response;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

import edu.hm.sisy.ssma.api.object.AbstractRessourceObject;

/**
 * Resource für die Antwort auf eine Authentifizierung.
 * 
 * @author Stefan Wörner
 */
@JsonPropertyOrder( value = { "token" }, alphabetic = true )
@Produces( { MediaType.APPLICATION_JSON } )
// @Consumes( { MediaType.APPLICATION_JSON } )
public class UserAuthenticationResponse extends AbstractRessourceObject
{

	private static final long serialVersionUID = -2222345699688098818L;

	private String m_sessionToken;

	/**
	 * Liefert das Attribut sessionToken.
	 * 
	 * @return sessionToken
	 */
	@JsonProperty( "token" )
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
	@JsonProperty( "token" )
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
		return new String[] { "m_sessionToken" };
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
				UserAuthenticationResponse.class, getExclusionList() );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.basic.object.AbstractBasicObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj )
	{
		return EqualsBuilder.reflectionEquals( this, obj, true, UserAuthenticationResponse.class, getExclusionList() );
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
		rsb.setUpToClass( UserAuthenticationResponse.class );
		rsb.setExcludeFieldNames( getExclusionList() );
		return rsb.toString();
	}
}
