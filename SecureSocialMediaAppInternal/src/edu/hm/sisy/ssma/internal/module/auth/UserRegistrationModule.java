package edu.hm.sisy.ssma.internal.module.auth;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import edu.hm.sisy.ssma.api.object.ApiConstants;
import edu.hm.sisy.ssma.api.object.resource.RegistrationUser;
import edu.hm.sisy.ssma.internal.bean.database.IUserDAOLocal;
import edu.hm.sisy.ssma.internal.object.entity.EntityUser;
import edu.hm.sisy.ssma.internal.object.exception.GenericUserRegistrationException;
import edu.hm.sisy.ssma.internal.util.CodecUtility;

/**
 * Registrierungsmodul für die Benutzer-Registrierung.
 * 
 * @author Stefan Wörner
 */
public class UserRegistrationModule extends BasicAuthenticationModule
{

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

			// Salt für Nutzer erzeugen und Base64 codieren
			byte[] saltBytes = genSalt();
			String saltBase64 = CodecUtility.byteToBase64( saltBytes );

			// Salted Passwort-Hash für Nutzer erzeugen und Base64 codieren
			byte[] digestBytes = genSaltedHash( user.getPassword(), saltBytes );
			String digestBase64 = CodecUtility.byteToBase64( digestBytes );

			// Salt für 2-Faktor Reset-Token erzeugen und Base64 codieren
			byte[] totpResetTokenSaltBytes = genSalt();
			String totpResetTokenSaltBase64 = CodecUtility.byteToBase64( totpResetTokenSaltBytes );

			// 2-Faktor Reset-Token für Nutzer erzeugen
			String totpResetTokenBase32 = genTotpResetToken();

			// Salted 2-Faktor Reset-Token-Hash erzeugen und Base64 codieren
			byte[] totpResetTokenDigestBytes = genSaltedHash( totpResetTokenBase32, totpResetTokenSaltBytes );
			String totpResetTokenDigestBase64 = CodecUtility.byteToBase64( totpResetTokenDigestBytes );

			// Benutzer Entität erzeugen
			EntityUser eUser = new EntityUser();
			eUser.setUsername( user.getUsername() );
			eUser.setTotpSecret( totpSecretBase32 );
			eUser.setDigest( digestBase64 );
			eUser.setSalt( saltBase64 );
			eUser.setTotpResetToken( totpResetTokenDigestBase64 );
			eUser.setTotpResetTokenSalt( totpResetTokenSaltBase64 );

			// Benutzer in der Datenbank speichern
			EntityUser eUserPersisted = m_userDAOBean.create( eUser );

			// TODO Prüfen ob Salt, SaltedHash oder TOTPSecret bereits von anderem Benutzer verwendet

			// Registrierungs QR-Code für Benutzer erzeugen
			String qrCodeUrl = getQRCodeURL( eUserPersisted.getUsername(), eUserPersisted.getTotpSecret() );

			// URL für QR-Code und TOTP Reset Token zurückgeben
			List<String> result = new ArrayList<String>();
			result.add( qrCodeUrl );
			result.add( totpResetTokenBase32 );
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
