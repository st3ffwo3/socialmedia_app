package edu.hm.sisy.ssma.service.bean.rest;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import edu.hm.sisy.ssma.api.communication.request.IRegistrationService;
import edu.hm.sisy.ssma.api.object.resource.RegistrationUser;
import edu.hm.sisy.ssma.api.object.resource.response.UserRegistrationResponse;
import edu.hm.sisy.ssma.internal.bean.AbstractBean;
import edu.hm.sisy.ssma.internal.bean.database.INodeDAOLocal;
import edu.hm.sisy.ssma.internal.bean.database.IUserDAOLocal;
import edu.hm.sisy.ssma.internal.interceptor.LoggingInterceptor;
import edu.hm.sisy.ssma.internal.module.auth.UserRegistrationModule;
import edu.hm.sisy.ssma.internal.module.node.NodeRegistrationModule;

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
		String qrCodeUrl = userRegModule.register( user );

		// Node-Registrierungsmodul initialisieren
		NodeRegistrationModule nodeRegModule = new NodeRegistrationModule( m_nodeDAOBean );
		// Node registrieren
		nodeRegModule.register( user.getNodeAddress() );

		// Response-Objekt erzeugen und QR-Code zurückgeben
		UserRegistrationResponse response = new UserRegistrationResponse();
		response.setQrCodeUrl( qrCodeUrl );
		return response;
	}
}
