package edu.hm.sisy.ssma.internal.object.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotEmpty;

import edu.hm.sisy.ssma.api.object.ErrorConstants;
import edu.hm.sisy.ssma.internal.object.AbstractEntityObject;

/**
 * Entität für die Knoten. Es werden Informationen zu den im System verfügbaren Knoten gespeichert.
 * 
 * @author Stefan Wörner
 */
@Entity
@Table( name = "ssma_node" )
public class EntityNode extends AbstractEntityObject
{

	private static final long serialVersionUID = 9098496380210100286L;

	@Id
	@Column( name = "address", length = 255, nullable = false, unique = true )
	@NotEmpty( message = ErrorConstants.NODE_ADDRESS_EMPTY_ERROR_MSG )
	private String m_address;

	@Temporal( TemporalType.TIMESTAMP )
	@Column( name = "last_updated", nullable = false )
	private Date m_lastUpdated;

	/**
	 * Liefert das Attribut address.
	 * 
	 * @return address
	 */
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
	public void setAddress( String address )
	{
		m_address = address;
	}

	/**
	 * Liefert das Attribut lastUpdated.
	 * 
	 * @return lastUpdated
	 */
	public Date getLastUpdated()
	{
		return m_lastUpdated;
	}

	/**
	 * Setzt das Attribut lastUpdated.
	 * 
	 * @param lastUpdated
	 *            zu setzender Wert für das Attribut lastUpdated
	 */
	public void setLastUpdated( Date lastUpdated )
	{
		m_lastUpdated = lastUpdated;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.basic.object.AbstractBasicObject#getExclusionList()
	 */
	@Override
	protected String[] getExclusionList()
	{
		return new String[] { "m_lastUpdated" };
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
				EntityNode.class, getExclusionList() );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.basic.object.AbstractBasicObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj )
	{
		return EqualsBuilder.reflectionEquals( this, obj, true, EntityNode.class, getExclusionList() );
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
		rsb.setUpToClass( EntityNode.class );
		rsb.setExcludeFieldNames( getExclusionList() );
		return rsb.toString();
	}
}
