package edu.hm.sisy.ssma.internal.module.auth;

import org.apache.commons.lang3.StringUtils;

import edu.hm.sisy.ssma.internal.bean.database.IUserDAOLocal;
import edu.hm.sisy.ssma.internal.session.SessionManager;

/**
 * Logoutmodul für die Benutzer-Abmeldung beim Logout.
 * 
 * @author Stefan Wörner
 */
public class UserLogoutModule extends BasicAuthenticationModule
{

	/**
	 * Standardkonstruktor.
	 * 
	 * @param userDAOBean
	 *            Referenz auf User DAO Bean
	 */
	public UserLogoutModule( IUserDAOLocal userDAOBean )
	{
		super( userDAOBean );
	}

	/**
	 * Invalidiert die Session eines Benutzers.
	 * 
	 * @param sessionToken
	 *            Session-Token
	 */
	public void invalidate( String sessionToken )
	{
		// Validierung der Eingabeparameter
		if (StringUtils.isBlank( sessionToken ))
		{
			// Keine Abmeldung möglich
			return;
		}

		// Session falls vorhanden invalidieren
		SessionManager.remove( SessionManager.retrieve( sessionToken ) );
	}
}
