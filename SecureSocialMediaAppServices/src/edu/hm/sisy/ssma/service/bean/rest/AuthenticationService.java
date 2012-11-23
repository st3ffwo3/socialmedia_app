package edu.hm.sisy.ssma.service.bean.rest;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import edu.hm.sisy.ssma.api.communication.request.IAuthenticationService;
import edu.hm.sisy.ssma.api.object.resource.AuthenticationUser;
import edu.hm.sisy.ssma.api.object.resource.response.UserAuthenticationResponse;
import edu.hm.sisy.ssma.internal.bean.AbstractBean;
import edu.hm.sisy.ssma.internal.bean.database.IUserDAOLocal;
import edu.hm.sisy.ssma.internal.interceptor.LoggingInterceptor;
import edu.hm.sisy.ssma.internal.module.auth.UserLoginModule;

/**
 * REST-Service für die Benutzerauthentifizierung. Verfügbare Aktionen: POST
 * 
 * @author Stefan Wörner
 */
@Stateless
@Interceptors( LoggingInterceptor.class )
public class AuthenticationService extends AbstractBean implements IAuthenticationService
{

	@EJB
	private IUserDAOLocal m_userDAOBean;

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.sisy.ssma.api.communication.request.IAuthenticationService
	 *      #authenticate(edu.hm.sisy.ssma.api.object.resource.AuthenticationUser)
	 */
	@Override
	public UserAuthenticationResponse authenticate( AuthenticationUser user )
	{
		// Loginmodul initialisieren
		UserLoginModule loginModule = new UserLoginModule( m_userDAOBean );
		// Benutzer authentifizieren
		String sessionToken = loginModule.authenticate( user );

		// Response-Objekt erzeugen und Session-Token zurückgeben
		UserAuthenticationResponse response = new UserAuthenticationResponse();
		response.setSessionToken( sessionToken );
		return response;
	}
}
