package edu.hm.sisy.ssma.internal.module.auth;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;

import edu.hm.sisy.ssma.api.object.ApiConstants;
import edu.hm.sisy.ssma.api.object.resource.BasicAuthUser;
import edu.hm.sisy.ssma.api.object.resource.LoginUser;
import edu.hm.sisy.ssma.api.object.resource.ReRegistrationUser;
import edu.hm.sisy.ssma.internal.bean.database.IUserDAOLocal;
import edu.hm.sisy.ssma.internal.object.entity.EntityUser;
import edu.hm.sisy.ssma.internal.object.exception.GenericUserAuthenticationException;
import edu.hm.sisy.ssma.internal.object.exception.UserAuthenticationFailedException;
import edu.hm.sisy.ssma.internal.session.SessionManager;
import edu.hm.sisy.ssma.internal.session.SsmsSession;
import edu.hm.sisy.ssma.internal.util.CodecUtility;

/**
 * Loginmodul für die Benutzer-Authentifizierung beim Login.
 * 
 * @author Stefan Wörner
 */
public class UserLoginModule extends BasicAuthenticationModule
{

	private static int TOTP_WINDOW_SIZE = 1;

	/**
	 * Standardkonstruktor.
	 * 
	 * @param userDAOBean
	 *            Referenz auf User DAO Bean
	 */
	public UserLoginModule( IUserDAOLocal userDAOBean )
	{
		super( userDAOBean );
	}

	/**
	 * Authentifiziert einen Benutzer anhand seines Benutzernamens, Passworts und dem TOTP-Token bzw. anhand seines
	 * Session-Token.
	 * 
	 * @param user
	 *            Zu authentifizierender Benutzer
	 * @param sessionToken
	 *            Session-Token
	 * @return Session-Token
	 */
	public SsmsSession authenticate( BasicAuthUser user, String sessionToken )
	{
		try
		{
			if (authenticateUser( user ))
			{
				if (user instanceof ReRegistrationUser)
				{
					// Es muss keine Session erzeugt werden
					return null;
				}

				// Session für Benutzer erzeugen und zurückgeben
				return SessionManager.create( user.getUsername() );
			}
			else if (authenticateSession( sessionToken ))
			{
				// Session laden
				SsmsSession session = SessionManager.retrieve( sessionToken );

				// Authentifizierung mit Benutzernamen/SessionToken war erfolgreich
				// => LastUpdated aktualisieren
				SessionManager.update( session );

				// Session zurückgeben
				return session;
			}

			// Session falls vorhanden invalidieren
			SessionManager.remove( SessionManager.retrieve( sessionToken ) );
			if (user != null)
			{
				SessionManager.remove( user.getUsername() );
			}

			// Exception werfen, da Authentifizierung fehlgeschlagen ist
			throw new UserAuthenticationFailedException();
		}
		catch (RuntimeException rex)
		{
			throw rex;
		}
		catch (Exception ex)
		{
			throw new GenericUserAuthenticationException();
		}
	}

	/**
	 * Authentifiziert einen Benutzer anhand seines Session-Token.
	 * 
	 * @param sessionToken
	 *            Session-Token
	 * @return Authentifizierungs-Flag
	 * @throws NoSuchAlgorithmException
	 *             Generierungs-Algorithmus nicht vorhanden
	 */
	private boolean authenticateSession( String sessionToken ) throws NoSuchAlgorithmException
	{
		boolean sessionExist = true;

		// Validierung der Eingabeparameter
		if (StringUtils.isBlank( sessionToken ))
		{
			// TIME RESISTANT ATTACK: Benötigte Zeit für Authentifizierung und Berechnungen muss identisch zur
			// benötigten Zeit bei korrekten Authentifizierungsparametern sein
			sessionExist = false;
			sessionToken = "000000000000000000000";
		}

		// Session suchen
		SsmsSession session = SessionManager.retrieve( sessionToken );

		if (session == null || StringUtils.isBlank( session.getSessionToken() ))
		{
			// TIME RESISTANT ATTACK: Benötigte Zeit für Authentifizierung und Berechnungen muss identisch zur
			// benötigten Zeit bei korrekten Authentifizierungsparametern sein
			sessionExist = false;

			session = new SsmsSession( "00000000000000" );
		}

		// Auth-Flag-Indikator mit userExist Flag initialisieren
		boolean authSuccessful = sessionExist;
		// Session-Token auf Gleichheit prüfen
		authSuccessful = authSuccessful && StringUtils.equals( sessionToken, session.getSessionToken() );

		// Auth-Flag-Indikator zurückgeben
		return authSuccessful;
	}

	/**
	 * Authentifiziert einen Benutzer anhand seines Benutzernamens, Passworts und dem TOTP-Token oder dem
	 * TOTP-Reset-Token.
	 * 
	 * @param user
	 *            Zu authentifizierender Benutzer
	 * @return Authentifizierungs-Flag
	 * @throws UnsupportedEncodingException
	 *             Ungültiges Encoding gewählt
	 * @throws NoSuchAlgorithmException
	 *             Generierungs-Algorithmus nicht vorhanden
	 */
	private boolean authenticateUser( BasicAuthUser user ) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		boolean userExist = true;

		// Validierung der Eingabeparameter
		if (user == null || StringUtils.isBlank( user.getUsername() ) || StringUtils.isBlank( user.getPassword() ))
		{
			// TIME RESISTANT ATTACK: Benötigte Zeit für Authentifizierung und Berechnungen muss identisch zur
			// benötigten Zeit bei korrekten Authentifizierungsparametern sein
			userExist = false;

			user = new BasicAuthUser();
			user.setUsername( "" );
			user.setPassword( "" );
		}
		else
		{
			// Base64 codiertes Passwort decodieren
			byte[] passwordBytes = CodecUtility.base64ToByte( user.getPassword() );
			user.setPassword( StringUtils.toString( passwordBytes, ApiConstants.DEFAULT_CHARSET ) );
		}

		// Benutzer in der Datenbank suchen
		EntityUser eUser = m_userDAOBean.read( user.getUsername() );

		if (eUser == null || StringUtils.isBlank( eUser.getDigest() ) || StringUtils.isBlank( eUser.getSalt() )
				|| StringUtils.isBlank( eUser.getTotpSecret() ))
		{
			// TIME RESISTANT ATTACK: Benötigte Zeit für Authentifizierung und Berechnungen muss identisch zur
			// benötigten Zeit bei korrekten Authentifizierungsparametern sein
			userExist = false;

			eUser = new EntityUser();
			eUser.setDigest( "000000000000000000000000000=" );
			eUser.setSalt( "00000000000=" );
			eUser.setTotpSecret( "0000000000000000" );
			eUser.setTotpResetToken( "0000000000000000" );
		}

		// Base64 codiertes Digest decodieren
		byte[] digestBytesStored = CodecUtility.base64ToByte( eUser.getDigest() );
		// Base64 codierten Salt decodieren
		byte[] saltBytesStored = CodecUtility.base64ToByte( eUser.getSalt() );

		// Neuen salted Passwort-Hash anhand des übergebenen Passworts berechnen
		byte[] digestBytes = genSaltedHash( user.getPassword(), saltBytesStored );

		// Auth-Flag-Indikator mit userExist Flag initialisieren
		boolean authSuccessful = userExist;
		// Salted Passwort-Hashes auf Gleichheit prüfen
		authSuccessful = authSuccessful && Arrays.equals( digestBytesStored, digestBytes );

		if (user instanceof LoginUser)
		{
			if (((LoginUser) user).getTotpToken() == null)
			{
				((LoginUser) user).setTotpToken( new Long( 0 ) );
			}

			// TOTP Token auf Korrektheit prüfen
			authSuccessful = authSuccessful && validateTotpToken( eUser.getTotpSecret(), ((LoginUser) user).getTotpToken() );
		}
		else if (user instanceof ReRegistrationUser)
		{
			if (((ReRegistrationUser) user).getResetToken() == null)
			{
				((ReRegistrationUser) user).setResetToken( "" );
			}

			// Base64 codierter 2-Faktor Reset-Token-Digest decodieren
			byte[] totpResetTokenDigestBytesStored = CodecUtility.base64ToByte( eUser.getTotpResetToken() );
			// Base64 codierter 2-Faktor Reset-Token-Salt decodieren
			byte[] totpResetTokenSaltBytesStored = CodecUtility.base64ToByte( eUser.getTotpResetTokenSalt() );

			// Neuen salted 2-Faktor Reset-Token-Hash anhand des übergebenen Token berechnen
			byte[] totpResetTokenDigestBytes = genSaltedHash(
					StringUtils.deleteWhitespace( ((ReRegistrationUser) user).getResetToken() ), totpResetTokenSaltBytesStored );

			// TOTP Token auf Korrektheit prüfen
			authSuccessful = authSuccessful && Arrays.equals( totpResetTokenDigestBytesStored, totpResetTokenDigestBytes );
		}
		else
		{
			authSuccessful = false;
		}

		// Auth-Flag-Indikator zurückgeben
		return authSuccessful;
	}

	/**
	 * Validiert den TOTP Token des Nutzers.
	 * 
	 * @param totpSecret
	 *            TOTP Secret Key
	 * @param totpToken
	 *            TOTP Token des Nutzers
	 * @return Gültigkeits-Flag
	 */
	private boolean validateTotpToken( String totpSecret, Long totpToken )
	{
		// Base32 codiertes TOTP Secret decodieren
		byte[] secretKey = CodecUtility.base32ToByte( totpSecret );

		// Aktuelle Systemzeit in ein 30 Sekunden Fenster konvertieren
		// (Siehe TOTP Spec - RFC6238)
		long t = (System.currentTimeMillis() / 1000L) / 30L;

		// Das WINDOW wird genutzt um vor kurzem abgelaufene Tokens ebenfalls zu prüfen
		// Standardmäßig ist das Fenster 1 Minute
		for (int i = -TOTP_WINDOW_SIZE; i <= TOTP_WINDOW_SIZE; ++i)
		{
			long hash;

			try
			{
				hash = genTotpVerificationToken( secretKey, t + i );
			}
			catch (Exception e)
			{
				return false;
			}

			// Token auf Gleichheit prüfen
			// Bei Gleichheit Schleife beenden und Erfolg zurückmelden
			if (totpToken.equals( hash ))
			{
				return true;
			}
		}

		// Token ist ungültig
		return false;
	}

	/**
	 * @see http://thegreyblog.blogspot.com/2011/12/google-authenticator-using-it-in-your.html
	 * @see http://code.google.com/p/google-authenticator
	 * @see https://tools.ietf.org/html/rfc6238
	 */
	private static int genTotpVerificationToken( byte[] key, long t ) throws NoSuchAlgorithmException, InvalidKeyException
	{
		byte[] data = new byte[8];
		long value = t;
		for (int i = 8; i-- > 0; value >>>= 8)
		{
			data[i] = (byte) value;
		}

		SecretKeySpec signKey = new SecretKeySpec( key, "HmacSHA1" );
		Mac mac = Mac.getInstance( "HmacSHA1" );
		mac.init( signKey );
		byte[] hash = mac.doFinal( data );

		int offset = hash[20 - 1] & 0xF;

		// We're using a long because Java hasn't got unsigned int.
		long truncatedHash = 0;
		for (int i = 0; i < 4; ++i)
		{
			truncatedHash <<= 8;
			// We are dealing with signed bytes:
			// we just keep the first byte.
			truncatedHash |= (hash[offset + i] & 0xFF);
		}

		truncatedHash &= 0x7FFFFFFF;
		truncatedHash %= 1000000;

		return (int) truncatedHash;
	}
}
