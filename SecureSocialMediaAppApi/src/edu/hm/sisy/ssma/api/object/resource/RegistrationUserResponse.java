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
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import edu.hm.sisy.ssma.api.object.AbstractRessourceObject;

/**
 * Resource für die Antwort auf eine Benutzerregistrierung.
 * 
 * @author Stefan Wörner
 */
@JsonPropertyOrder( value = { "qrcode" }, alphabetic = true )
@JsonSerialize( include = Inclusion.NON_NULL )
@Produces( { MediaType.APPLICATION_JSON } )
@Consumes( { MediaType.APPLICATION_JSON } )
public class RegistrationUserResponse extends AbstractRessourceObject
{

	private static final long serialVersionUID = 774880264543015352L;

	private String m_qrCodeUrl;

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
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.basic.object.AbstractBasicObject#getExclusionList()
	 */
	@Override
	protected String[] getExclusionList()
	{
		return new String[] { "m_qrCodeUrl" };
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
				RegistrationUserResponse.class, getExclusionList() );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.basic.object.AbstractBasicObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj )
	{
		return EqualsBuilder.reflectionEquals( this, obj, true, RegistrationUserResponse.class, getExclusionList() );
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
		rsb.setUpToClass( RegistrationUserResponse.class );
		rsb.setExcludeFieldNames( getExclusionList() );
		return rsb.toString();
	}
}
