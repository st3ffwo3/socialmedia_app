package edu.hm.sisy.ssma.internal.module;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;

import edu.hm.basic.logging.BasicLogger;
import edu.hm.sisy.ssma.api.object.ApiConstants;
import edu.hm.sisy.ssma.api.object.resource.AuthenticationUser;
import edu.hm.sisy.ssma.internal.bean.database.IUserDAOLocal;
import edu.hm.sisy.ssma.internal.object.entity.EntityUser;
import edu.hm.sisy.ssma.internal.util.CodecUtility;

/**
 * Loginmodul für die Benutzer-Authentifizierung beim Login.
 * 
 * @author Stefan Wörner
 */
public class LoginModule extends BaseAuthenticationModule
{

	private static final int SSMA_TOKEN_SIZE = 20;

	private static int TOTP_WINDOW_SIZE = 1;

	/**
	 * Standardkonstruktor.
	 * 
	 * @param userDAOBean
	 *            Referenz auf User DAO Bean
	 */
	public LoginModule( IUserDAOLocal userDAOBean )
	{
		super( userDAOBean );
	}

	// TODO Möglichkeit vorsehen um Benutzer nur anhand des SSMA Tokens zu authentifizieren

	/**
	 * Authentifiziert einen Benutzer anhand seines Benutzernamens, Passworts und dem TOTP Token.
	 * 
	 * @param user
	 *            Zu authentifizierender Benutzer
	 * @return Authentifizierungs-Flag
	 */
	public boolean authenticateUser( AuthenticationUser user )
	{
		try
		{
			boolean userExist = true;

			// Validierung der Eingabeparameter
			if (user == null || StringUtils.isBlank( user.getUsername() ) || StringUtils.isBlank( user.getPassword() )
					|| user.getTotpToken() == null)
			{
				// TIME RESISTANT ATTACK: Benötigte Zeit für Authentifizierung und Berechnungen muss identisch zur
				// benötigten Zeit bei korrekten Authentifizierungsparametern sein
				userExist = false;

				user = new AuthenticationUser();
				user.setUsername( "" );
				user.setPassword( "" );
				user.setTotpToken( new Long( 0 ) );
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
			// TOTP Token auf Korrektheit prüfen
			authSuccessful = authSuccessful && validateTotpToken( eUser.getTotpSecret(), user.getTotpToken() );

			// TODO SSMA Token generieren und im Nutzer speichern
			// TODO SSMA Token zurückgeben statt boolean

			// Auth-Flag-Indikator zurückgeben
			return authSuccessful;
		}
		catch (Exception ex)
		{
			// TODO FineTuning Exception Handling
			return false;
		}
	}

	/**
	 * Generiert einen zufälligen Token für die SSMA Session und gibt diesen Base64 codiert zurück.
	 * 
	 * @return Base64 codierter SSMA Token
	 * @throws NoSuchAlgorithmException
	 *             Algorithmus existiert nicht
	 */
	private static String genSsmaToken() throws NoSuchAlgorithmException
	{
		// Secure Random Instanz erzeugen um Zufallswerte zu erzeugen
		SecureRandom random = SecureRandom.getInstance( RANDOM_GENERATION_ALGORITHM );
		// Buffer für Token initialisieren mit einer Länge von 160 bits
		byte[] ssmaTokenBytes = new byte[SSMA_TOKEN_SIZE];
		// Buffer mit Zufallswerten befüllen
		random.nextBytes( ssmaTokenBytes );

		// Token Base64 encodieren
		String ssmaToken = CodecUtility.byteToBase64( ssmaTokenBytes );

		// Token zurückgeben
		return ssmaToken;
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
				hash = genTotpToken( secretKey, t + i );
			}
			catch (Exception e)
			{
				// TODO FineTuning Exception Handling
				BasicLogger.logError( this, e.getMessage() );
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
	private static int genTotpToken( byte[] key, long t ) throws NoSuchAlgorithmException, InvalidKeyException
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
