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
 * Resource für die Antwort auf eine Benutzerregistrierung.
 * 
 * @author Stefan Wörner
 */
@JsonPropertyOrder( value = { "qrcode" }, alphabetic = true )
@Produces( { MediaType.APPLICATION_JSON } )
// @Consumes( { MediaType.APPLICATION_JSON } )
public class UserRegistrationResponse extends AbstractRessourceObject
{

	private static final long serialVersionUID = 774880264543015352L;

	private String m_qrCodeUrl;

	private String m_resetToken;

	/**
	 * Liefert das Attribut qrCodeUrl.
	 * 
	 * @return qrCodeUrl
	 */
	@JsonProperty( "qrcode" )
	public String getQrCodeUrl()
	{
		return m_qrCodeUrl;
	}

	/**
	 * Setzt das Attribut qrCodeUrl.
	 * 
	 * @param qrCodeUrl
	 *            zu setzender Wert für das Attribut qrCodeUrl
	 */
	@JsonProperty( "qrcode" )
	public void setQrCodeUrl( String qrCodeUrl )
	{
		m_qrCodeUrl = qrCodeUrl;
	}

	/**
	 * Liefert das Attribut resetToken.
	 * 
	 * @return resetToken
	 */
	@JsonProperty( "reset-token" )
	public String getResetToken()
	{
		return m_resetToken;
	}

	/**
	 * Setzt das Attribut resetToken.
	 * 
	 * @param resetToken
	 *            zu setzender Wert für das Attribut resetToken
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
		return new String[] { "m_qrCodeUrl", "m_resetToken" };
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
				UserRegistrationResponse.class, getExclusionList() );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.basic.object.AbstractBasicObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj )
	{
		return EqualsBuilder.reflectionEquals( this, obj, true, UserRegistrationResponse.class, getExclusionList() );
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
		rsb.setUpToClass( UserRegistrationResponse.class );
		rsb.setExcludeFieldNames( getExclusionList() );
		return rsb.toString();
	}
}
