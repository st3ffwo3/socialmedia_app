package edu.hm.sisy.ssma.internal.module.auth;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import org.apache.commons.codec.digest.DigestUtils;

import edu.hm.sisy.ssma.api.object.ApiConstants;
import edu.hm.sisy.ssma.internal.bean.database.IUserDAOLocal;
import edu.hm.sisy.ssma.internal.object.exception.UnsafeCredentialException;
import edu.hm.sisy.ssma.internal.util.CodecUtility;

/**
 * Basisklasse für die Registrierung und Authentifizierung von Benutzern.
 * 
 * @author Stefan Wörner
 */
public class BasicAuthenticationModule
{

	private static final String STRONG_PASSWORD_PATTERN = ""
			+ "(?=^.{10,}$)(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&amp;*()_+}{&quot;:;'?/&gt;.&lt;,])(?!.*\\s).*$";

	private static final int TOTP_RESET_TOKEN_SIZE = 10;

	private static final String TWO_FACTOR_APPLICATION_NAME = "SSMS";

	private static final int TWO_FACTOR_SECRET_SIZE = 10;

	private static final int TWO_FACTOR_NUM_OF_SCRATCH_CODES = 5;

	private static final int TWO_FACTOR_SCRATCH_CODE_SIZE = 8;

	private static final String TWO_FACTOR_QRCODE_BASE_URL = "https://chart.googleapis.com/"
			+ "chart?chs=200x200&cht=qr&chld=M|0&chl=otpauth://totp/%s:%s?secret=%s";

	/**
	 * Anzahl an Iterationen für salted Hash Berechnung.
	 */
	protected static final int NUM_SALTED_HASH_ITERATIONS = 5;

	/**
	 * Secure Random Generierungsalgorithmus.
	 */
	protected static final String RANDOM_GENERATION_ALGORITHM = "SHA1PRNG";

	/**
	 * Referenz auf User DAO Bean.
	 */
	protected IUserDAOLocal m_userDAOBean;

	/**
	 * Standardkonstruktor.
	 * 
	 * @param userDAOBean
	 *            Referenz auf User DAO Bean
	 */
	protected BasicAuthenticationModule( IUserDAOLocal userDAOBean )
	{
		m_userDAOBean = userDAOBean;
	}

	/**
	 * Berechnet den salted Hash für das übergebene Passwort.
	 * 
	 * @param credential
	 *            Zu verschlüsselndes Credential
	 * @param salt
	 *            Zu verwendender Salt
	 * @return Verschlüsseltes Passwort (SaltedHash)
	 * @throws NoSuchAlgorithmException
	 *             Hash-Algorithmus existiert nicht
	 * @throws UnsupportedEncodingException
	 *             Encoding wird nicht unterstützt
	 */
	protected static byte[] genSaltedHash( String credential, byte[] salt ) throws NoSuchAlgorithmException,
			UnsupportedEncodingException
	{
		return genSaltedHash( NUM_SALTED_HASH_ITERATIONS, credential, salt );
	}

	/**
	 * Berechnet den salted Hash für das übergebene Passwort. Dabei werden 1 + n Iterationen durchgeführt.
	 * 
	 * @param iterations
	 *            Anzahl der anzuwendenden Iterationen
	 * @param credential
	 *            Zu verschlüsselndes Credential
	 * @param salt
	 *            Zu verwendender Salt
	 * @return Verschlüsseltes Passwort (SaltedHash)
	 * @throws NoSuchAlgorithmException
	 *             Hash-Algorithmus existiert nicht
	 * @throws UnsupportedEncodingException
	 *             Encoding wird nicht unterstützt
	 */
	private static byte[] genSaltedHash( int iterations, String credential, byte[] salt ) throws NoSuchAlgorithmException,
			UnsupportedEncodingException
	{
		// Digest initialisieren (SHA-512)
		MessageDigest digest = DigestUtils.getSha512Digest();
		// Digest zurücksetzen
		digest.reset();
		// Digest mit Salt aktualisieren
		digest.update( salt );
		// Salted Hash für Passwort berechnen (Iteration 1)
		byte[] hash = digest.digest( credential.getBytes( ApiConstants.DEFAULT_CHARSET ) );

		for (int i = 0; i < iterations; i++)
		{
			// Digest wieder zurücksetzen
			digest.reset();
			// Neuen salted Hash für Passwort berechnen (Iteration 1 + n)
			hash = digest.digest( hash );
		}

		// Salted Hash zurückgeben
		return hash;
	}

	/**
	 * Generiert einen zufälligen Token für den Reset der TOTP Verknüpfung und gibt diesen Base32 codiert zurück.
	 * 
	 * @return Base32 codierter TOTP Reset Token
	 * @throws NoSuchAlgorithmException
	 *             Algorithmus existiert nicht
	 */
	protected static String genTotpResetToken() throws NoSuchAlgorithmException
	{
		// Secure Random Instanz erzeugen um Zufallswerte zu erzeugen
		SecureRandom random = SecureRandom.getInstance( RANDOM_GENERATION_ALGORITHM );
		// Buffer für Token initialisieren mit einer Länge von 80 bits => Ergibt Base32 codiert 16 Zeichen
		byte[] resetTokenBytes = new byte[TOTP_RESET_TOKEN_SIZE];
		// Buffer mit Zufallswerten befüllen
		random.nextBytes( resetTokenBytes );

		// Token Base32 encodieren
		String resetToken = CodecUtility.byteToBase32( resetTokenBytes );

		// Token zurückgeben
		return resetToken.toUpperCase();
	}

	/**
	 * Generiert das 2-Faktor Secret für die Kopplung mit dem Google Authenticator.
	 * 
	 * @return 2-Faktor Secret
	 * @throws NoSuchAlgorithmException
	 *             Algorithmus existiert nicht
	 */
	protected static byte[] genTotpSecret() throws NoSuchAlgorithmException
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
	 * Generiert einen zufälligen Salt für die Erzeugung von salted Passwort-Hashs.
	 * 
	 * @return Salt
	 * @throws NoSuchAlgorithmException
	 *             Algorithmus existiert nicht
	 */
	protected static byte[] genSalt() throws NoSuchAlgorithmException
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
	 * Gibt die URL zur Generierung und Anzeige des QR-Codes zurück. Der Benutzer fotografiert diese mit der Google
	 * Authenticator App auf dem Smartphone ab, um den Secret Key abzuspeichern.
	 * 
	 * @param username
	 *            Benutzername
	 * @param totpSecret
	 *            2-Faktor Secret
	 * @return URL für den QR-Code
	 */
	protected static String getQRCodeURL( String username, String totpSecret )
	{
		return String.format( TWO_FACTOR_QRCODE_BASE_URL, TWO_FACTOR_APPLICATION_NAME, username, totpSecret );
	}

	/**
	 * Prüft die Komplexität des Passworts und wirft bei zu einfachen Passwörtern eine Exception.
	 * 
	 * @param credential
	 *            Passwort
	 */
	protected static void validateCredentialComplexity( String credential )
	{
		if (!credential.matches( STRONG_PASSWORD_PATTERN ))
		{
			throw new UnsafeCredentialException();
		}
	}
}
