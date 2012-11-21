package edu.hm.sisy.ssma.api.object;

/**
 * Klasse enthält alle Konstanten der SecureSocialMediaApp API die auch fuer Clients notwendig bzw. sinnvoll sind.
 * 
 * @author Stefan Wörner
 */
public final class ApiConstants
{

	/**
	 * Privater Konstructor.
	 */
	private ApiConstants()
	{

	}

	/**
	 * Konstante mit der Versionsnummer der Server-Anteile.
	 */
	public static final String VERSION = "1.000.0";

	/**
	 * Datumsformatierungs-String.
	 */
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

	/**
	 * Default Charset.
	 */
	public static final String DEFAULT_CHARSET = "UTF-8";
}
