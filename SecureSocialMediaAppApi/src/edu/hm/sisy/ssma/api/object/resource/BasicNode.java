package edu.hm.sisy.ssma.api.object.resource;

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
 * Basis-Resource für einen Node im Cluster/System.
 * 
 * @author Stefan Wörner
 */
@JsonPropertyOrder( value = { "address" }, alphabetic = true )
@Produces( { MediaType.APPLICATION_JSON } )
// @Consumes( { MediaType.APPLICATION_JSON } )
public class BasicNode extends AbstractRessourceObject
{

	private static final long serialVersionUID = -3780056032930739461L;

	private String m_address;

	/**
	 * Liefert das Attribut address.
	 * 
	 * @return address
	 */
	@JsonProperty( "address" )
	public String getAddress()
	{
		return m_address;
	}

	/**
	 * Setzt das Attribut address.
	 * 
	 * @param address
	 *            zu setzender Wert für das Attribut address
	 */
	@JsonProperty( "address" )
	public void setAddress( String address )
	{
		m_address = address;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.basic.object.AbstractBasicObject#getExclusionList()
	 */
	@Override
	protected String[] getExclusionList()
	{
		return new String[] {};
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
				BasicNode.class, getExclusionList() );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.basic.object.AbstractBasicObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj )
	{
		return EqualsBuilder.reflectionEquals( this, obj, true, BasicNode.class, getExclusionList() );
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
		rsb.setUpToClass( BasicNode.class );
		rsb.setExcludeFieldNames( getExclusionList() );
		return rsb.toString();
	}
}
