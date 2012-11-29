package edu.hm.sisy.ssma.internal.module.auth;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import edu.hm.sisy.ssma.api.object.ApiConstants;
import edu.hm.sisy.ssma.api.object.resource.RegistrationUser;
import edu.hm.sisy.ssma.internal.bean.database.IUserDAOLocal;
import edu.hm.sisy.ssma.internal.object.entity.EntityUser;
import edu.hm.sisy.ssma.internal.object.exception.GenericUserRegistrationException;
import edu.hm.sisy.ssma.internal.object.exception.UnsafeCredentialException;
import edu.hm.sisy.ssma.internal.util.CodecUtility;

/**
 * Registrierungsmodul für die Benutzer-Registrierung.
 * 
 * @author Stefan Wörner
 */
public class UserRegistrationModule extends BasicAuthenticationModule
{

	private static final String STRONG_PASSWORD_PATTERN = ""
			+ "(?=^.{10,}$)(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&amp;*()_+}{&quot;:;'?/&gt;.&lt;,])(?!.*\\s).*$";

	/**
	 * Standardkonstruktor.
	 * 
	 * @param userDAOBean
	 *            Referenz auf User DAO Bean
	 */
	public UserRegistrationModule( IUserDAOLocal userDAOBean )
	{
		super( userDAOBean );
	}

	/**
	 * Registriert einen Benutzer anhand seines Benutzernamens und Passworts.
	 * 
	 * @param user
	 *            Zu registrierender Benutzer
	 * @return Registrierungs QR-Code URL und TOTP Reset Token
	 */
	public List<String> register( RegistrationUser user )
	{
		try
		{
			// Base64 codiertes Passwort decodieren
			byte[] passwordBytes = CodecUtility.base64ToByte( user.getPassword() );
			user.setPassword( StringUtils.toString( passwordBytes, ApiConstants.DEFAULT_CHARSET ) );

			// Passwortkomplexität prüfen
			validateCredentialComplexity( user.getPassword() );

			// 2-Faktor Secret für Nutzer erzeugen und Base32 codieren
			byte[] totpSecretBytes = genTotpSecret();
			String totpSecretBase32 = CodecUtility.byteToBase32( totpSecretBytes );

			// 2-Faktor Reset-Token für Nutzer erzeugen
			String totpResetTokenBase32 = genTotpResetToken();

			// Salt für Nutzer erzeugen und Base64 codieren
			byte[] saltBytes = genSalt();
			String saltBase64 = CodecUtility.byteToBase64( saltBytes );

			// Salted Passwort-Hash für Nutzer erzeugen und Base64 codieren
			byte[] digestBytes = genSaltedHash( user.getPassword(), saltBytes );
			String digestBase64 = CodecUtility.byteToBase64( digestBytes );

			// Benutzer Entität erzeugen
			EntityUser eUser = new EntityUser();
			eUser.setUsername( user.getUsername() );
			eUser.setTotpSecret( totpSecretBase32 );
			eUser.setDigest( digestBase64 );
			eUser.setSalt( saltBase64 );
			eUser.setTotpResetToken( totpResetTokenBase32 );

			// Benutzer in der Datenbank speichern
			EntityUser eUserPersisted = m_userDAOBean.create( eUser );

			// TODO Prüfen ob Salt, SaltedHash oder TOTPSecret bereits von anderem Benutzer verwendet

			// Registrierungs QR-Code für Benutzer erzeugen
			String qrCodeUrl = getQRCodeURL( eUserPersisted.getUsername(), eUserPersisted.getTotpSecret() );

			// URL für QR-Code und TOTP Reset Token zurückgeben
			List<String> result = new ArrayList<String>();
			result.add( qrCodeUrl );
			result.add( eUserPersisted.getTotpResetToken() );
			return result;
		}
		catch (RuntimeException rex)
		{
			throw rex;
		}
		catch (Exception ex)
		{
			throw new GenericUserRegistrationException();
		}
	}

	private static void validateCredentialComplexity( String credential )
	{
		if (!credential.matches( STRONG_PASSWORD_PATTERN ))
		{
			throw new UnsafeCredentialException();
		}
	}

	/**
	 * Generiert einen zufälligen Salt für die Erzeugung von salted Passwort-Hashs.
	 * 
	 * @return Salt
	 * @throws NoSuchAlgorithmException
	 *             Algorithmus existiert nicht
	 */
	private static byte[] genSalt() throws NoSuchAlgorithmException
	{
		// Secure Random Instanz erzeugen um Zufallswerte zu erzeugen
		SecureRandom random = SecureRandom.getInstance( RANDOM_GENERATION_ALGORITHM );
		// Buffer für Salt initialisieren mit einer Länge von 64 bits
		byte[] salt = new byte[8];
		// Buffer mit Zufallswerten befüllen
		random.nextBytes( salt );

		// Salt zurückgeben
		return salt;
	}
}
