package edu.hm.sisy.ssma.internal.session;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import edu.hm.sisy.ssma.internal.object.AbstractInternalObject;
import edu.hm.sisy.ssma.internal.util.CodecUtility;

/**
 * Klasse hält alle benötigten Informationen einer Benutzersession.
 * 
 * @author Stefan Wörner
 */
public class SsmsSession extends AbstractInternalObject
{

	private static final long serialVersionUID = -3117675031775464182L;

	private static final String RANDOM_GENERATION_ALGORITHM = "SHA1PRNG";

	private static final int SESSION_TOKEN_SIZE = 20;

	private static final String SSMS_TOKEN_SEPARATOR = "|";

	private String m_username;

	private String m_sessionToken;

	/**
	 * Konstruktor.
	 * 
	 * @param username
	 *            Benutzername
	 * @throws NoSuchAlgorithmException
	 *             Generierungs-Algorithmus nicht vorhanden
	 */
	public SsmsSession( String username ) throws NoSuchAlgorithmException
	{
		m_username = username;
		m_sessionToken = genSessionToken();
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
	 * Liefert das Base64 codierte Session-Token.
	 * 
	 * @return Session-Token (Bestehend aus Nutzername und SessionToken)
	 */
	public String getSessionToken()
	{
		String token = m_username + SSMS_TOKEN_SEPARATOR + m_sessionToken;
		return CodecUtility.byteToBase64( token.getBytes() );
	}

	/**
	 * Generiert einen zufälligen Token für die SSMA Session und gibt diesen Base64 codiert zurück.
	 * 
	 * @return Base64 codierter SSMA Token
	 * @throws NoSuchAlgorithmException
	 *             Algorithmus existiert nicht
	 */
	private static String genSessionToken() throws NoSuchAlgorithmException
	{
		// Secure Random Instanz erzeugen um Zufallswerte zu erzeugen
		SecureRandom random = SecureRandom.getInstance( RANDOM_GENERATION_ALGORITHM );
		// Buffer für Token initialisieren mit einer Länge von 160 bits
		byte[] sessionTokenBytes = new byte[SESSION_TOKEN_SIZE];
		// Buffer mit Zufallswerten befüllen
		random.nextBytes( sessionTokenBytes );

		// Token Base64 encodieren
		String sessionToken = CodecUtility.byteToBase64( sessionTokenBytes );

		// Token zurückgeben
		return sessionToken;
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
				SsmsSession.class, getExclusionList() );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.basic.object.AbstractBasicObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj )
	{
		return EqualsBuilder.reflectionEquals( this, obj, true, SsmsSession.class, getExclusionList() );
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
		rsb.setUpToClass( SsmsSession.class );
		rsb.setExcludeFieldNames( getExclusionList() );
		return rsb.toString();
	}
}
