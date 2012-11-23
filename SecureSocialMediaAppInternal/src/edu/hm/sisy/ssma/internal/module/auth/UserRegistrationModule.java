package edu.hm.sisy.ssma.internal.module.auth;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

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
public class UserRegistrationModule extends BaseAuthenticationModule
{

	private static final String STRONG_PASSWORD_PATTERN = ""
			+ "(?=^.{10,}$)(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&amp;*()_+}{&quot;:;'?/&gt;.&lt;,])(?!.*\\s).*$";

	private static final String TWO_FACTOR_APPLICATION_NAME = "SSMS";

	private static final int TWO_FACTOR_SECRET_SIZE = 10;

	private static final int TWO_FACTOR_NUM_OF_SCRATCH_CODES = 5;

	private static final int TWO_FACTOR_SCRATCH_CODE_SIZE = 8;

	private static final String TWO_FACTOR_QRCODE_BASE_URL = "https://chart.googleapis.com/"
			+ "chart?chs=200x200&cht=qr&chld=M|0&chl=otpauth://totp/%s:%s?secret=%s";

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

	// TODO Möglichkeit vorsehen um Benutzer zu recovern bei Verlust von Smartphone (z.B. über SuperToken à la Dropbox)

	/**
	 * Registriert einen Benutzer anhand seines Benutzernamens und Passworts.
	 * 
	 * @param user
	 *            Zu registrierender Benutzer
	 * @return Registrierungs QR-Code URL
	 */
	public String register( RegistrationUser user )
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

			// Benutzer in der Datenbank speichern
			EntityUser eUserPersisted = m_userDAOBean.create( eUser );

			// TODO Prüfen ob Salt, SaltedHash oder TOTPSecret bereits von anderem Benutzer verwendet

			// Registrierungs QR-Code für Benutzer erzeugen
			String qrCodeUrl = getQRCodeURL( eUserPersisted.getUsername(), eUserPersisted.getTotpSecret() );
			// URL für QR-Code zurückgeben
			return qrCodeUrl;
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

	/**
	 * Generiert das 2-Faktor Secret für die Kopplung mit dem Google Authenticator.
	 * 
	 * @return 2-Faktor Secret
	 * @throws NoSuchAlgorithmException
	 *             Algorithmus existiert nicht
	 */
	private static byte[] genTotpSecret() throws NoSuchAlgorithmException
	{
		// Secure Random Instanz erzeugen um Zufallswerte zu erzeugen
		SecureRandom random = SecureRandom.getInstance( RANDOM_GENERATION_ALGORITHM );
		// Buffer initialisieren mit einer Länge von 2640 bits (80 + 40 * 64)
		byte[] buffer = new byte[TWO_FACTOR_SECRET_SIZE + TWO_FACTOR_NUM_OF_SCRATCH_CODES * TWO_FACTOR_SCRATCH_CODE_SIZE];
		// Buffer mit Zufallswerten befüllen
		random.nextBytes( buffer );

		// Secret Key mit 10 Bytes erzeugen
		byte[] secretKey = Arrays.copyOf( buffer, TWO_FACTOR_SECRET_SIZE );

		// Secret Key zurückgeben
		return secretKey;
	}

	/**
	 * Gibt die URL zur Generierung und Anzeige des QR-Codes zurück. Der Benutzer fotografiert diese mit der Google
	 * Authenticator App auf dem Smartphone ab, um den Secret Key abzuspeichern.
	 * 
	 * @param username
	 *            Benutzername
	 * @param totpSecret
	 *            2-Faktor Secret
	 * @return URL für den QR-Code
	 */
	private static String getQRCodeURL( String username, String totpSecret )
	{
		return String.format( TWO_FACTOR_QRCODE_BASE_URL, TWO_FACTOR_APPLICATION_NAME, username, totpSecret );
	}
}
