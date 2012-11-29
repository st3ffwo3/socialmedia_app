package edu.hm.sisy.ssma.service.bean.rest;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import edu.hm.sisy.ssma.api.communication.request.IAuthenticationService;
import edu.hm.sisy.ssma.api.communication.request.IUserService;
import edu.hm.sisy.ssma.api.object.resource.BasicAuthUser;
import edu.hm.sisy.ssma.internal.bean.AbstractBean;
import edu.hm.sisy.ssma.internal.bean.database.IUserDAOLocal;
import edu.hm.sisy.ssma.internal.interceptor.AuthenticationInterceptor;
import edu.hm.sisy.ssma.internal.interceptor.LoggingInterceptor;
import edu.hm.sisy.ssma.internal.module.user.UserUpdateModule;
import edu.hm.sisy.ssma.internal.object.exception.IllegalUserUpdateException;
import edu.hm.sisy.ssma.internal.object.ressource.TokenUser;

/**
 * REST-Service für die Benutzerauthentifizierung. Verfügbare Aktionen: POST
 * 
 * @author Stefan Wörner
 */
@Stateless
@Interceptors( { LoggingInterceptor.class, AuthenticationInterceptor.class } )
public class UserService extends AbstractBean implements IUserService
{

	@EJB
	private IUserDAOLocal m_userDAOBean;

	@EJB
	private IAuthenticationService m_authenticationService;

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.sisy.ssma.api.communication.request.IUserService#changePassword(edu.hm.sisy.ssma.api.object.resource.BasicAuthUser,
	 *      java.lang.String, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void changePassword( BasicAuthUser user, String ssmsToken, HttpServletResponse response )
	{
		TokenUser tUser = new TokenUser( ssmsToken );

		if (!StringUtils.equalsIgnoreCase( user.getUsername(), tUser.getUsername() ))
		{
			throw new IllegalUserUpdateException();
		}

		// Benutzer-Aktualisierungsmodul initialisieren
		UserUpdateModule userUpdateModule = new UserUpdateModule( m_userDAOBean );
		// Benutzer aktualisieren
		userUpdateModule.changePassword( user );
	}
}
