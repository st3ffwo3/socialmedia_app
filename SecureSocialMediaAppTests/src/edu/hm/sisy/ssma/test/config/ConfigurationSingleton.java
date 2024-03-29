package edu.hm.sisy.ssma.test.config;

/**
 * Singleton Implementierung für den globalen Zugriff auf die Konfiguration.
 * 
 * @author Stefan Wörner
 */
public final class ConfigurationSingleton extends SecureSocialMediaAppConfiguration
{

	private static ConfigurationSingleton m_Config = new ConfigurationSingleton();

	/**
	 * Konstruktor.
	 */
	private ConfigurationSingleton()
	{
		super( ConfigurationConstants.CONFIG_FILE_NAME );
	}

	/**
	 * Singleton Factory Methode.
	 * 
	 * @return unique Instanz von {@link ConfigurationSingleton}
	 */
	public static ConfigurationSingleton getInstance()
	{
		synchronized (m_Config)
		{
			return m_Config;
		}
	}
}
