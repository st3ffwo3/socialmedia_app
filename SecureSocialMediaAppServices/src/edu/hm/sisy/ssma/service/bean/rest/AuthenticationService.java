package edu.hm.sisy.ssma.service.bean.rest;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletResponse;

import edu.hm.sisy.ssma.api.communication.request.IAuthenticationService;
import edu.hm.sisy.ssma.api.object.resource.LoginUser;
import edu.hm.sisy.ssma.internal.bean.AbstractBean;
import edu.hm.sisy.ssma.internal.bean.database.IUserDAOLocal;
import edu.hm.sisy.ssma.internal.interceptor.LoggingInterceptor;
import edu.hm.sisy.ssma.internal.module.auth.UserLoginModule;
import edu.hm.sisy.ssma.internal.module.auth.UserLogoutModule;
import edu.hm.sisy.ssma.internal.object.ressource.TokenUser;

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
	 * @see edu.hm.sisy.ssma.api.communication.request.IAuthenticationService#login(edu.hm.sisy.ssma.api.object.resource.LoginUser,
	 *      java.lang.String, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void login( LoginUser user, String ssmsToken, HttpServletResponse response )
	{
		// SSMS Token decodieren und TokenUser erstellen
		TokenUser tUser = new TokenUser( ssmsToken );

		// Loginmodul initialisieren
		UserLoginModule loginModule = new UserLoginModule( m_userDAOBean );
		// Benutzer authentifizieren
		ssmsToken = loginModule.authenticate( user, tUser );
		// SSMS-Token in den Header setzten
		response.setHeader( "ssms-token", ssmsToken );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.sisy.ssma.api.communication.request.IAuthenticationService#logout(java.lang.String)
	 */
	@Override
	public void logout( String ssmsToken )
	{
		// SSMS Token decodieren und TokenUser erstellen
		TokenUser tUser = new TokenUser( ssmsToken );

		// Logoutmodul initialisieren
		UserLogoutModule logoutModule = new UserLogoutModule( m_userDAOBean );
		// Benutzer abmelden und Session invalidieren
		logoutModule.invalidate( tUser );
	}
}
