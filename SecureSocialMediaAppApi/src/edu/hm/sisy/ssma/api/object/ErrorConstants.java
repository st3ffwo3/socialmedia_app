package edu.hm.sisy.ssma.api.object;

/**
 * Klasse enthält alle Fehler-Konstanten der SecureSocialMediaApp API.
 * 
 * @author Stefan Wörner
 */
public final class ErrorConstants
{

	/**
	 * Privater Konstructor.
	 */
	private ErrorConstants()
	{

	}

	/**
	 * Fehlermeldung für einen leeren Benutzernamen.
	 */
	public static final String USER_NAME_EMPTY_ERROR_MSG = "Bitte geben Sie Ihren Benutzernamen an.";

	/**
	 * Fehlermeldung für einen ungültigen Benutzernamen.
	 */
	public static final String USER_NAME_ILLEGAL_EMAIL_ERROR_MSG = "Bitte geben Sie eine gültige E-Mail Adresse als Benutzername an.";

	/**
	 * Fehlermeldung für ein leeres Passwort.
	 */
	public static final String USER_PASSWORD_EMPTY_ERROR_MSG = "Bitte geben Sie Ihr Passwort an.";

	/**
	 * Fehlermeldung für einen leeren TOTP Token.
	 */
	public static final String USER_TOTP_TOKEN_EMPTY_ERROR_MSG = "Bitte geben Sie Ihr aktuelles zeitbasiertes Einmalpasswort an.";

	/**
	 * Fehlermeldung für eine leere Node Adresse.
	 */
	public static final String NODE_ADDRESS_EMPTY_ERROR_MSG = "Die Adresse des Nodes wurde nicht übertragen.";
}
