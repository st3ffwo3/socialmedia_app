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
	 * Fehlermeldung für einen ungültigen Benutzernamen.
	 */
	public static final String USER_NAME_EXISTS_ERROR_MSG = "Der angegeben Benutzername ist bereits vergeben oder gesperrt.";

	/**
	 * Fehlermeldung für ein leeres Passwort.
	 */
	public static final String USER_PASSWORD_EMPTY_ERROR_MSG = "Bitte geben Sie Ihr Passwort an.";

	/**
	 * Fehlermeldung für ein unsicheres Passwort.
	 */
	public static final String USER_PASSWORD_UNSAFE_ERROR_MSG = "Bitte verwenden Sie ein sicheres Passwort. "
			+ "Sichere Passwörter bestehen aus mindestens einer Zahl, einem Großbuchstaben, einem Kleinbuchstaben, "
			+ "einem Sonderzeichen und sind mindestens 8 Zeichen lang.";

	/**
	 * Fehlermeldung für einen leeren TOTP Token.
	 */
	public static final String USER_TOTP_TOKEN_EMPTY_ERROR_MSG = "Bitte geben Sie Ihr aktuelles zeitbasiertes Einmalpasswort an.";

	/**
	 * Fehlermeldung für eine leere Node Adresse.
	 */
	public static final String NODE_ADDRESS_EMPTY_ERROR_MSG = "Die Adresse des Nodes wurde nicht übertragen.";

	/**
	 * Fehlermeldung für allgemeine Fehler bei der Benutzerregistrierung.
	 */
	public static final String GENERIC_USER_REGISTRATION_FAILURE_ERROR_MSG = "Bei der Benutzerregistrierung ist ein Fehler aufgetreten. "
			+ "Versuchen Sie es später wieder.";

	/**
	 * Fehlermeldung für allgemeine Fehler bei der Benutzeranmeldung.
	 */
	public static final String GENERIC_USER_AUTHENTICATION_FAILURE_ERROR_MSG = "Bei der Anmeldung ist ein Fehler aufgetreten. "
			+ "Versuchen Sie es später wieder.";

	/**
	 * Fehlermeldung für eine fehlgeschlagene Authentifizierung.
	 */
	public static final String USER_AUTHENTICATION_FAILED_ERROR_MSG = "Die Andmeldung ist fehlgeschlagen: Ihre Anmeldedaten sind nicht korrekt.";
}
