package edu.hm.sisy.ssma.service.bean.rest;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletResponse;

import edu.hm.sisy.ssma.api.communication.request.IUserService;
import edu.hm.sisy.ssma.api.object.resource.PwdChangeUser;
import edu.hm.sisy.ssma.internal.bean.AbstractBean;
import edu.hm.sisy.ssma.internal.bean.database.IUserDAOLocal;
import edu.hm.sisy.ssma.internal.interceptor.AuthenticationInterceptor;
import edu.hm.sisy.ssma.internal.interceptor.LoggingInterceptor;
import edu.hm.sisy.ssma.internal.module.user.UserUpdateModule;

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

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.sisy.ssma.api.communication.request.IUserService#changePassword(edu.hm.sisy.ssma.api.object.resource.PwdChangeUser,
	 *      java.lang.String, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void changePassword( PwdChangeUser user, String ssmsToken, HttpServletResponse response )
	{
		// Benutzer-Aktualisierungsmodul initialisieren
		UserUpdateModule userUpdateModule = new UserUpdateModule( m_userDAOBean );
		// Benutzer aktualisieren
		userUpdateModule.changePassword( user, ssmsToken );
	}
}
