package edu.hm.sisy.ssma.internal.session;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.apache.commons.lang3.ObjectUtils;

import edu.hm.basic.object.Tupel;

/**
 * SessionManager: Verwaltet die Benutzersessions.
 * 
 * @author Stefan Wörner
 */
public final class SessionManager
{

	private static final long SESSION_TIME_FRAME = 10 * 60 * 1000; // 10 Minuten in Millisekunden

	private SessionManager()
	{

	}

	/**
	 * Erzeugt eine neue Session.
	 * 
	 * @param username
	 *            Benutzername
	 * @return SSMS Session
	 * @throws NoSuchAlgorithmException
	 *             Generierungs-Algorithmus nicht vorhanden
	 */
	public static SsmsSession create( String username ) throws NoSuchAlgorithmException
	{
		SsmsSession session = new SsmsSession( username );
		SessionStore.getInstance().store( session, new Date() );
		return session;
	}

	/**
	 * Gibt die SSMS Session für das SessionToken zurück.
	 * 
	 * @param sessionToken
	 *            Session-Token
	 * @return SSMS Session, kann NULL sein
	 */
	public static SsmsSession retrieve( String sessionToken )
	{
		Tupel<SsmsSession, Date> tupel = SessionStore.getInstance().load( sessionToken );

		if (tupel == null || tupel.getFirst() == null)
		{
			return null;
		}

		if (tupel.getSecond() == null || !sessionIsValid( tupel.getSecond().getTime() ))
		{
			SessionStore.getInstance().delete( tupel.getFirst().getUsername() );
			return null;
		}

		return tupel.getFirst();
	}

	/**
	 * Aktualisert die SSMS Session.
	 * 
	 * @param session
	 *            SSMS Session
	 */
	public static void update( SsmsSession session )
	{
		SsmsSession sessionStored = retrieve( session.getSessionToken() );

		if (sessionStored == null || !ObjectUtils.equals( sessionStored, session ))
		{
			return;
		}

		SessionStore.getInstance().store( sessionStored, new Date() );
	}

	/**
	 * Löscht die Session.
	 * 
	 * @param session
	 *            SSMS-Session
	 */
	public static void remove( SsmsSession session )
	{
		if (session == null)
		{
			return;
		}

		SessionStore.getInstance().delete( session.getUsername() );
	}

	/**
	 * Löscht die Session des Benutzers.
	 * 
	 * @param username
	 *            Benutzername
	 */
	public static void remove( String username )
	{
		SessionStore.getInstance().delete( username );
	}

	private static boolean sessionIsValid( long lastUpdated )
	{
		// Aktuelle Zeit abzüglich des Zeitfensers von 10 Minuten muss kleiner (älter) sein als das letzte
		// Aktualisierungsdatum der Session
		return (new Date().getTime() - SESSION_TIME_FRAME) < lastUpdated;
	}
}
