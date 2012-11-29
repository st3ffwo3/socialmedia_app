package edu.hm.sisy.ssma.internal.object.ressource;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import edu.hm.sisy.ssma.api.object.ApiConstants;
import edu.hm.sisy.ssma.api.object.resource.BasicUser;
import edu.hm.sisy.ssma.internal.object.exception.GenericUserAuthenticationException;
import edu.hm.sisy.ssma.internal.util.CodecUtility;

/**
 * Ressource für einen TokenUser.
 * 
 * @author Stefan Wörner
 */
public class TokenUser extends BasicUser
{

	private static final long serialVersionUID = 483366905638510113L;

	private static final String SSMS_TOKEN_SEPARATOR = "|";

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
		setUsername( username );
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
				setUsername( "" );
				m_sessionToken = "";
				return;
			}

			byte[] tokenBytes = CodecUtility.base64ToByte( ssmsToken );
			String token = StringUtils.toString( tokenBytes, ApiConstants.DEFAULT_CHARSET );

			String[] tokenSplit = StringUtils.split( token, SSMS_TOKEN_SEPARATOR );
			setUsername( tokenSplit[0] );
			m_sessionToken = tokenSplit[1];
		}
		catch (Exception ex)
		{
			throw new GenericUserAuthenticationException();
		}
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
		String token = getUsername() + SSMS_TOKEN_SEPARATOR + m_sessionToken;
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
		return ArrayUtils.addAll( super.getExclusionList(), new String[] { "m_sessionToken" } );
	}
}
