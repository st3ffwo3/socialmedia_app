package edu.hm.sisy.ssma.internal.module.auth;

import org.apache.commons.lang3.StringUtils;

import edu.hm.sisy.ssma.api.object.resource.BasicUser;
import edu.hm.sisy.ssma.internal.bean.database.IUserDAOLocal;
import edu.hm.sisy.ssma.internal.object.entity.EntityUser;

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
	 * @param user
	 *            Zu invalidierender Benutzer
	 */
	public void invalidate( BasicUser user )
	{
		// Validierung der Eingabeparameter
		if (user == null || StringUtils.isBlank( user.getUsername() ))
		{
			// Keine Abmeldung möglich
			return;
		}

		// Benutzer in der Datenbank suchen
		EntityUser eUser = m_userDAOBean.read( user.getUsername() );

		if (eUser == null)
		{
			// Benutzer nicht gefunden, keine Abmeldung möglich
			return;
		}

		// Sessioninformationen invalidieren
		eUser.setSessionToken( null );
		eUser.setSessionTokenLastUpdated( null );

		// Invalidierte Sessioninformationen persistieren
		m_userDAOBean.update( eUser );
	}
}
