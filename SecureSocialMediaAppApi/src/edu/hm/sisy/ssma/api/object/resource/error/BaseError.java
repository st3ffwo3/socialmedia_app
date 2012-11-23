package edu.hm.sisy.ssma.api.object.resource.error;

import java.util.ArrayList;
import java.util.List;

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
 * Basis-Resource für einen Fehler.
 * 
 * @author Stefan Wörner
 */
@JsonPropertyOrder( value = { "messages" }, alphabetic = true )
@JsonSerialize( include = Inclusion.NON_NULL )
@Produces( { MediaType.APPLICATION_JSON } )
@Consumes( { MediaType.APPLICATION_JSON } )
public class BaseError extends AbstractRessourceObject
{

	private static final long serialVersionUID = 8008611549154925609L;

	private List<String> m_messages = new ArrayList<String>();

	/**
	 * Liefert das Attribut messages.
	 * 
	 * @return messages
	 */
	@JsonProperty( "messages" )
	public List<String> getMessages()
	{
		return m_messages;
	}

	/**
	 * Setzt das Attribut messages.
	 * 
	 * @param messages
	 *            zu setzender Wert für das Attribut messages
	 */
	@JsonProperty( "messages" )
	public void setMessages( List<String> messages )
	{
		m_messages = messages;
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
				BaseError.class, getExclusionList() );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.basic.object.AbstractBasicObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj )
	{
		return EqualsBuilder.reflectionEquals( this, obj, true, BaseError.class, getExclusionList() );
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
		rsb.setUpToClass( BaseError.class );
		rsb.setExcludeFieldNames( getExclusionList() );
		return rsb.toString();
	}
}
