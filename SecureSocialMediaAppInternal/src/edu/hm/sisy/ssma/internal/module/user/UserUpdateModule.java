package edu.hm.sisy.ssma.internal.module.user;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import edu.hm.sisy.ssma.api.object.ApiConstants;
import edu.hm.sisy.ssma.api.object.resource.PwdChangeUser;
import edu.hm.sisy.ssma.internal.bean.database.IUserDAOLocal;
import edu.hm.sisy.ssma.internal.module.auth.BasicAuthenticationModule;
import edu.hm.sisy.ssma.internal.object.entity.EntityUser;
import edu.hm.sisy.ssma.internal.object.exception.GenericUserUpdateException;
import edu.hm.sisy.ssma.internal.object.exception.IllegalUserUpdateException;
import edu.hm.sisy.ssma.internal.object.exception.OldPasswordNotMatchException;
import edu.hm.sisy.ssma.internal.session.SessionManager;
import edu.hm.sisy.ssma.internal.session.SsmsSession;
import edu.hm.sisy.ssma.internal.util.CodecUtility;

/**
 * Aktualisierungsmodul für die Benutzer.
 * 
 * @author Stefan Wörner
 */
public class UserUpdateModule extends BasicAuthenticationModule
{

	/**
	 * Standardkonstruktor.
	 * 
	 * @param userDAOBean
	 *            Referenz auf User DAO Bean
	 */
	public UserUpdateModule( IUserDAOLocal userDAOBean )
	{
		super( userDAOBean );
	}

	/**
	 * Ändert das Passwort eines Benutzers.
	 * 
	 * @param user
	 *            Benutzer
	 * @param sessionToken
	 *            Session-Token
	 */
	public void changePassword( PwdChangeUser user, String sessionToken )
	{
		SsmsSession session = SessionManager.retrieve( sessionToken );

		if (!StringUtils.equalsIgnoreCase( session.getUsername(), user.getUsername() ))
		{
			throw new IllegalUserUpdateException();
		}

		try
		{
			// Altes Base64 codiertes Passwort decodieren
			byte[] passwordOldBytes = CodecUtility.base64ToByte( user.getOldPassword() );
			user.setOldPassword( StringUtils.toString( passwordOldBytes, ApiConstants.DEFAULT_CHARSET ) );

			// Neues Base64 codiertes Passwort decodieren
			byte[] passwordBytes = CodecUtility.base64ToByte( user.getPassword() );
			user.setPassword( StringUtils.toString( passwordBytes, ApiConstants.DEFAULT_CHARSET ) );

			// Benutzer in der Datenbank suchen
			EntityUser eUser = m_userDAOBean.read( user.getUsername() );

			// Base64 codiertes Digest decodieren
			byte[] digestOldBytesStored = CodecUtility.base64ToByte( eUser.getDigest() );
			// Base64 codierten Salt decodieren
			byte[] saltOldBytesStored = CodecUtility.base64ToByte( eUser.getSalt() );

			// Salted Passwort-Hash anhand des übergebenen alten Passworts berechnen
			byte[] digestOldBytes = genSaltedHash( user.getOldPassword(), saltOldBytesStored );

			if (!Arrays.equals( digestOldBytesStored, digestOldBytes ))
			{
				throw new OldPasswordNotMatchException();
			}

			// Passwortkomplexität des neuen Passworts prüfen
			validateCredentialComplexity( user.getPassword() );

			// Salt für Nutzer erzeugen und Base64 codieren
			byte[] saltBytes = genSalt();
			String saltBase64 = CodecUtility.byteToBase64( saltBytes );

			// Salted Passwort-Hash für Nutzer erzeugen und Base64 codieren
			byte[] digestBytes = genSaltedHash( user.getPassword(), saltBytes );
			String digestBase64 = CodecUtility.byteToBase64( digestBytes );

			// Benutzer Entität mit neuer Credentials Konfig aktualisieren
			eUser.setDigest( digestBase64 );
			eUser.setSalt( saltBase64 );

			// Benutzer in der Datenbank speichern
			m_userDAOBean.update( eUser );
		}
		catch (RuntimeException rex)
		{
			throw rex;
		}
		catch (Exception ex)
		{
			throw new GenericUserUpdateException();
		}
	}
}
