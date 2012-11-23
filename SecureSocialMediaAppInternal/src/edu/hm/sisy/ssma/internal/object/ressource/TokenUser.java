package edu.hm.sisy.ssma.internal.object.ressource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import edu.hm.sisy.ssma.api.object.AbstractRessourceObject;
import edu.hm.sisy.ssma.api.object.ApiConstants;
import edu.hm.sisy.ssma.internal.object.exception.GenericUserAuthenticationException;
import edu.hm.sisy.ssma.internal.util.CodecUtility;

/**
 * Ressource für einen TokenUser.
 * 
 * @author Stefan Wörner
 */
public class TokenUser extends AbstractRessourceObject
{

	private static final long serialVersionUID = 483366905638510113L;

	private static final String SSMS_TOKEN_SEPARATOR = "|";

	private String m_username;

	private String m_sessionToken;

	/**
	 * Konstruktor mit "Klartext".
	 * 
	 * @param username
	 *            Benutzername
	 * @param sessionToken
	 *            Session-Token
	 */
	public TokenUser( String username, String sessionToken )
	{
		m_username = username;
		m_sessionToken = sessionToken;
	}

	/**
	 * Konstruktor der Base64 codierte SSMS-Token zerlegt und TokenUser Objekt erzeugt.
	 * 
	 * @param ssmsToken
	 *            SSMS-Token (Bestehend aus Nutzername und SessionToken)
	 */
	public TokenUser( String ssmsToken )
	{
		try
		{
			if (ssmsToken == null)
			{
				m_username = "";
				m_sessionToken = "";
				return;
			}

			byte[] tokenBytes = CodecUtility.base64ToByte( ssmsToken );
			String token = StringUtils.toString( tokenBytes, ApiConstants.DEFAULT_CHARSET );

			String[] tokenSplit = StringUtils.split( token, SSMS_TOKEN_SEPARATOR );
			m_username = tokenSplit[0];
			m_sessionToken = tokenSplit[1];
		}
		catch (Exception ex)
		{
			throw new GenericUserAuthenticationException();
		}
	}

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
	 * Liefert das Attribut sessionToken.
	 * 
	 * @return sessionToken
	 */
	public String getSessionToken()
	{
		return m_sessionToken;
	}

	/**
	 * Liefert das Base64 codierte SSMS-Token.
	 * 
	 * @return SSMS-Token (Bestehend aus Nutzername und SessionToken)
	 */
	public String getSsmsToken()
	{
		String token = m_username + SSMS_TOKEN_SEPARATOR + m_sessionToken;
		return CodecUtility.byteToBase64( token.getBytes() );
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
				TokenUser.class, getExclusionList() );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.basic.object.AbstractBasicObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj )
	{
		return EqualsBuilder.reflectionEquals( this, obj, true, TokenUser.class, getExclusionList() );
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
		rsb.setUpToClass( TokenUser.class );
		rsb.setExcludeFieldNames( getExclusionList() );
		return rsb.toString();
	}
}
