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
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import edu.hm.sisy.ssma.internal.object.AbstractEntityObject;

/**
 * Entität für den Benutzer. Es werden alle benötigten Benutzer-Information in dieser Klasse gehalten, wie
 * beispielsweise der Benutzername, das Password, der Password Salt usw.
 * 
 * @author Stefan Wörner
 */
@Entity
@Table( name = "ssma_user" )
public class EntityUser extends AbstractEntityObject
{

	private static final long serialVersionUID = -4931591312319154051L;

	private static final String USERNAME_EMPTY_ERROR_MSG = "Bitte geben sie einen Benutzernamen an.";

	private static final String USERNAME_VALIDATION_ERROR_MSG = "Bitte geben sie eine gültige E-Mail Adresse als Benutzername an.";

	private static final String PASSWORD_EMPTY_ERROR_MSG = "Bitte geben sie ein Passwort an.";

	@Id
	@Column( name = "username", length = 255, nullable = false, unique = true )
	@NotEmpty( message = USERNAME_EMPTY_ERROR_MSG )
	@Email( message = USERNAME_VALIDATION_ERROR_MSG )
	private String m_username;

	@Column( name = "pwd", length = 255, nullable = false )
	@NotEmpty( message = PASSWORD_EMPTY_ERROR_MSG )
	private String m_digest;

	@Column( name = "salt", length = 255, nullable = false )
	private String m_salt;

	@Column( name = "secret", length = 255, nullable = true )
	private String m_totpSecret;

	@Column( name = "token", length = 255, nullable = true )
	private String m_ssmaToken;

	@Temporal( TemporalType.TIMESTAMP )
	@Column( name = "tokenLastUpdated", nullable = true )
	private Date m_ssmaTokenLastUpdated;

	/**
	 * Liefert das Attribut username.
	 * 
	 * @return username
	 */
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
	public void setUsername( String username )
	{
		m_username = username;
	}

	/**
	 * Liefert das Attribut digest.
	 * 
	 * @return digest
	 */
	public String getDigest()
	{
		return m_digest;
	}

	/**
	 * Setzt das Attribut digest.
	 * 
	 * @param digest
	 *            zu setzender Wert für das Attribut digest
	 */
	public void setDigest( String digest )
	{
		m_digest = digest;
	}

	/**
	 * Liefert das Attribut salt.
	 * 
	 * @return salt
	 */
	public String getSalt()
	{
		return m_salt;
	}

	/**
	 * Setzt das Attribut salt.
	 * 
	 * @param salt
	 *            zu setzender Wert für das Attribut salt
	 */
	public void setSalt( String salt )
	{
		m_salt = salt;
	}

	/**
	 * Liefert das Attribut totpSecret.
	 * 
	 * @return totpSecret
	 */
	public String getTotpSecret()
	{
		return m_totpSecret;
	}

	/**
	 * Setzt das Attribut totpSecret.
	 * 
	 * @param totpSecret
	 *            zu setzender Wert für das Attribut totpSecret
	 */
	public void setTotpSecret( String totpSecret )
	{
		m_totpSecret = totpSecret;
	}

	/**
	 * Liefert das Attribut ssmaToken.
	 * 
	 * @return ssmaToken
	 */
	public String getSsmaToken()
	{
		return m_ssmaToken;
	}

	/**
	 * Setzt das Attribut ssmaToken.
	 * 
	 * @param ssmaToken
	 *            zu setzender Wert für das Attribut ssmaToken
	 */
	public void setSsmaToken( String ssmaToken )
	{
		m_ssmaToken = ssmaToken;
	}

	/**
	 * Liefert das Attribut ssmaTokenLastUpdated.
	 * 
	 * @return ssmaTokenLastUpdated
	 */
	public Date getSsmaTokenLastUpdated()
	{
		return m_ssmaTokenLastUpdated;
	}

	/**
	 * Setzt das Attribut ssmaTokenLastUpdated.
	 * 
	 * @param ssmaTokenLastUpdated
	 *            zu setzender Wert für das Attribut ssmaTokenLastUpdated
	 */
	public void setSsmaTokenLastUpdated( Date ssmaTokenLastUpdated )
	{
		m_ssmaTokenLastUpdated = ssmaTokenLastUpdated;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.basic.object.AbstractBasicObject#getExclusionList()
	 */
	@Override
	protected String[] getExclusionList()
	{
		return new String[] { "m_digest", "m_salt", "m_totpSecret", "m_ssmaToken" };
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
				EntityUser.class, getExclusionList() );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.basic.object.AbstractBasicObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj )
	{
		return EqualsBuilder.reflectionEquals( this, obj, true, EntityUser.class, getExclusionList() );
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
		rsb.setUpToClass( EntityUser.class );
		rsb.setExcludeFieldNames( getExclusionList() );
		return rsb.toString();
	}
}
