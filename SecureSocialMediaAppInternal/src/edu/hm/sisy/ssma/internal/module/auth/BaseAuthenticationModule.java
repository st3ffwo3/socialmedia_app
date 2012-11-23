package edu.hm.sisy.ssma.internal.module.auth;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.DigestUtils;

import edu.hm.sisy.ssma.api.object.ApiConstants;
import edu.hm.sisy.ssma.internal.bean.database.IUserDAOLocal;

/**
 * Basisklasse für die Registrierung und Authentifizierung von Benutzern.
 * 
 * @author Stefan Wörner
 */
public class BaseAuthenticationModule
{

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
	protected BaseAuthenticationModule( IUserDAOLocal userDAOBean )
	{
		m_userDAOBean = userDAOBean;
	}

	/**
	 * Anzahl an Iterationen für salted Hash Berechnung.
	 */
	protected static final int NUM_SALTED_HASH_ITERATIONS = 5;

	/**
	 * Secure Random Generierungsalgorithmus.
	 */
	protected static final String RANDOM_GENERATION_ALGORITHM = "SHA1PRNG";

	/**
	 * Berechnet den salted Hash für das übergebene Passwort.
	 * 
	 * @param password
	 *            Zu verschlüsselndes Passwort
	 * @param salt
	 *            Zu verwendender Salt
	 * @return Verschlüsseltes Passwort (SaltedHash)
	 * @throws NoSuchAlgorithmException
	 *             Hash-Algorithmus existiert nicht
	 * @throws UnsupportedEncodingException
	 *             Encoding wird nicht unterstützt
	 */
	protected static byte[] genSaltedHash( String password, byte[] salt ) throws NoSuchAlgorithmException,
			UnsupportedEncodingException
	{
		return genSaltedHash( NUM_SALTED_HASH_ITERATIONS, password, salt );
	}

	/**
	 * Berechnet den salted Hash für das übergebene Passwort. Dabei werden 1 + n Iterationen durchgeführt.
	 * 
	 * @param iterations
	 *            Anzahl der anzuwendenden Iterationen
	 * @param password
	 *            Zu verschlüsselndes Passwort
	 * @param salt
	 *            Zu verwendender Salt
	 * @return Verschlüsseltes Passwort (SaltedHash)
	 * @throws NoSuchAlgorithmException
	 *             Hash-Algorithmus existiert nicht
	 * @throws UnsupportedEncodingException
	 *             Encoding wird nicht unterstützt
	 */
	protected static byte[] genSaltedHash( int iterations, String password, byte[] salt ) throws NoSuchAlgorithmException,
			UnsupportedEncodingException
	{
		// Digest initialisieren (SHA-512)
		MessageDigest digest = DigestUtils.getSha512Digest();
		// Digest zurücksetzen
		digest.reset();
		// Digest mit Salt aktualisieren
		digest.update( salt );
		// Salted Hash für Passwort berechnen (Iteration 1)
		byte[] hash = digest.digest( password.getBytes( ApiConstants.DEFAULT_CHARSET ) );

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
}
