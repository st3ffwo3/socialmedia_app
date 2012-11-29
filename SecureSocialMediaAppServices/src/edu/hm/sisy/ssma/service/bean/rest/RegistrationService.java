package edu.hm.sisy.ssma.service.bean.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import edu.hm.sisy.ssma.api.communication.request.IRegistrationService;
import edu.hm.sisy.ssma.api.object.resource.ReRegistrationUser;
import edu.hm.sisy.ssma.api.object.resource.RegistrationUser;
import edu.hm.sisy.ssma.api.object.resource.response.UserRegistrationResponse;
import edu.hm.sisy.ssma.internal.bean.AbstractBean;
import edu.hm.sisy.ssma.internal.bean.database.INodeDAOLocal;
import edu.hm.sisy.ssma.internal.bean.database.IUserDAOLocal;
import edu.hm.sisy.ssma.internal.interceptor.LoggingInterceptor;
import edu.hm.sisy.ssma.internal.module.auth.UserReRegistrationModule;
import edu.hm.sisy.ssma.internal.module.auth.UserRegistrationModule;
import edu.hm.sisy.ssma.internal.module.node.NodeRegistrationModule;
import edu.hm.sisy.ssma.internal.util.StringUtility;

/**
 * REST-Service für die Benutzerregistrierung. Verfügbare Aktionen: POST
 * 
 * @author Stefan Wörner
 */
@Stateless
@Interceptors( LoggingInterceptor.class )
public class RegistrationService extends AbstractBean implements IRegistrationService
{

	@EJB
	private IUserDAOLocal m_userDAOBean;

	@EJB
	private INodeDAOLocal m_nodeDAOBean;

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.sisy.ssma.api.communication.request.IRegistrationService#register(edu.hm.sisy.ssma.api.object.resource.RegistrationUser)
	 */
	@Override
	public UserRegistrationResponse register( RegistrationUser user )
	{
		// Benutzer-Registrierungsmodul initialisieren
		UserRegistrationModule userRegModule = new UserRegistrationModule( m_userDAOBean );
		// Benutzer registrieren
		List<String> regResult = userRegModule.register( user );

		// Node-Registrierungsmodul initialisieren
		NodeRegistrationModule nodeRegModule = new NodeRegistrationModule( m_nodeDAOBean );
		// Node registrieren
		nodeRegModule.register( user.getNodeAddress() );

		// Response-Objekt erzeugen
		UserRegistrationResponse response = new UserRegistrationResponse();
		// QR-Code hinzufügen
		response.setQrCodeUrl( regResult.get( 0 ) );
		// TOTP Reset Token hinzufügen: Zur besseren Lesbarkeit für Nutzer spliten
		response.setResetToken( StringUtility.splitString( regResult.get( 1 ), " ", 4 ) );
		return response;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.sisy.ssma.api.communication.request.IRegistrationService#reRegister(edu.hm.sisy.ssma.api.object.resource.ReRegistrationUser)
	 */
	@Override
	public UserRegistrationResponse reRegister( ReRegistrationUser user )
	{
		// Benutzer-Registrierungsmodul initialisieren
		UserReRegistrationModule userReRegModule = new UserReRegistrationModule( m_userDAOBean );
		// Benutzer registrieren
		List<String> reRegResult = userReRegModule.reRegister( user );

		// Response-Objekt erzeugen
		UserRegistrationResponse response = new UserRegistrationResponse();
		// QR-Code hinzufügen
		response.setQrCodeUrl( reRegResult.get( 0 ) );
		// TOTP Reset Token hinzufügen: Zur besseren Lesbarkeit für Nutzer spliten
		response.setResetToken( StringUtility.splitString( reRegResult.get( 1 ), " ", 4 ) );
		return response;
	}
}
