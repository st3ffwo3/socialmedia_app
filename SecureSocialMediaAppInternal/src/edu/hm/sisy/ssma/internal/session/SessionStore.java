package edu.hm.sisy.ssma.internal.session;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import edu.hm.basic.object.Tupel;

/**
 * Singleton-Implementierung eines Session Stores.
 * 
 * @author Stefan Wörner
 */
public final class SessionStore
{

	private static SessionStore instance;

	private Map<String, Tupel<SsmsSession, Date>> m_sessionStore;

	private SessionStore()
	{
		m_sessionStore = Collections.synchronizedMap( new HashMap<String, Tupel<SsmsSession, Date>>() );
	}

	/**
	 * Liefert eine Instanz des SessionStore zurück.
	 * 
	 * @return SessionStore Instanz
	 */
	public static SessionStore getInstance()
	{
		if (instance == null)
		{
			instance = new SessionStore();
		}
		return instance;
	}

	/**
	 * Speichert eine Session.
	 * 
	 * @param session
	 *            SSMS Session
	 * @param timestamp
	 *            Aktualisierungsdatum
	 */
	public void store( SsmsSession session, Date timestamp )
	{
		synchronized (m_sessionStore)
		{
			if (session == null || StringUtils.isBlank( session.getUsername() )
					|| StringUtils.isBlank( session.getSessionToken() ))
			{
				return;
			}
			m_sessionStore.put( session.getUsername(), new Tupel<SsmsSession, Date>( session, timestamp ) );
		}
	}

	/**
	 * Lädt die Session anhand des Session-Tokens.
	 * 
	 * @param sessionToken
	 *            Session-Token
	 * @return Tupel bestehend aus SSMS Session und Aktualisierungsdatum
	 */
	public Tupel<SsmsSession, Date> load( String sessionToken )
	{
		synchronized (m_sessionStore)
		{
			for (String key : m_sessionStore.keySet())
			{
				if (StringUtils.equals( m_sessionStore.get( key ).getFirst().getSessionToken(), sessionToken ))
				{
					return m_sessionStore.get( key );
				}
			}

			return null;
		}
	}

	/**
	 * Löscht die Session des Benutzers.
	 * 
	 * @param username
	 *            Benutzername
	 */
	public void delete( String username )
	{
		synchronized (m_sessionStore)
		{
			if (StringUtils.isBlank( username ))
			{
				return;
			}
			m_sessionStore.remove( username );
		}
	}
}
