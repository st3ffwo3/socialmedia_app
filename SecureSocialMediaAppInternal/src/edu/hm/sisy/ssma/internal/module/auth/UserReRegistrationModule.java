package edu.hm.sisy.ssma.internal.module.auth;

import java.util.ArrayList;
import java.util.List;

import edu.hm.sisy.ssma.api.object.resource.ReRegistrationUser;
import edu.hm.sisy.ssma.internal.bean.database.IUserDAOLocal;
import edu.hm.sisy.ssma.internal.object.entity.EntityUser;
import edu.hm.sisy.ssma.internal.object.exception.GenericUserRegistrationException;
import edu.hm.sisy.ssma.internal.util.CodecUtility;

/**
 * Registrierungsmodul für die Benutzer-Registrierung.
 * 
 * @author Stefan Wörner
 */
public class UserReRegistrationModule extends BasicAuthenticationModule
{

	private UserLoginModule m_loginModule;

	/**
	 * Standardkonstruktor.
	 * 
	 * @param userDAOBean
	 *            Referenz auf User DAO Bean
	 */
	public UserReRegistrationModule( IUserDAOLocal userDAOBean )
	{
		super( userDAOBean );
		m_loginModule = new UserLoginModule( userDAOBean );
	}

	/**
	 * Nimmt eine erneute Registrierung einen Benutzer anhand seines Benutzernamens, Passworts und TOTP Reset Tokens
	 * vor.
	 * 
	 * @param user
	 *            Zu re-registrierender Benutzer
	 * @return Neuer registrierungs QR-Code URL und neuer TOTP Reset Token
	 */
	public List<String> reRegister( ReRegistrationUser user )
	{
		// Benutzer authentifizieren
		// => Bei fehlerhafter Authentifizierung wird Exception geworfen und weitere Verarbeitung abgebrochen
		m_loginModule.authenticate( user );

		try
		{
			// Benutzer in der Datenbank suchen
			EntityUser eUser = m_userDAOBean.read( user.getUsername() );

			// Neues 2-Faktor Secret für Nutzer erzeugen und Base32 codieren
			byte[] totpSecretBytes = genTotpSecret();
			String totpSecretBase32 = CodecUtility.byteToBase32( totpSecretBytes );

			// Neuen 2-Faktor Reset-Token für Nutzer erzeugen
			String totpResetTokenBase32 = genTotpResetToken();

			// Benutzer Entität mit neuer TOTP Konfig aktualisieren
			eUser.setTotpSecret( totpSecretBase32 );
			eUser.setTotpResetToken( totpResetTokenBase32 );
			// Session invalidieren
			eUser.setSessionToken( null );
			eUser.setSessionTokenLastUpdated( null );

			// Benutzer in der Datenbank speichern
			EntityUser eUserUpdated = m_userDAOBean.update( eUser );

			// Registrierungs QR-Code für Benutzer erzeugen
			String qrCodeUrl = getQRCodeURL( eUserUpdated.getUsername(), eUserUpdated.getTotpSecret() );

			// URL für QR-Code und TOTP Reset Token zurückgeben
			List<String> result = new ArrayList<String>();
			result.add( qrCodeUrl );
			result.add( eUserUpdated.getTotpResetToken() );
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
}