package edu.hm.sisy.ssma.service.bean.rest;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import edu.hm.sisy.ssma.api.communication.request.IRegistrationService;
import edu.hm.sisy.ssma.api.object.resource.RegistrationUser;
import edu.hm.sisy.ssma.internal.bean.AbstractBean;
import edu.hm.sisy.ssma.internal.bean.database.IUserDAOLocal;
import edu.hm.sisy.ssma.internal.interceptor.LoggingInterceptor;
import edu.hm.sisy.ssma.internal.module.RegistrationModule;

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

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.sisy.ssma.api.communication.request.IRegistrationService#register(edu.hm.sisy.ssma.api.object.resource.RegistrationUser)
	 */
	@Override
	public String register( RegistrationUser user )
	{
		// Registrierungsmodul initialisieren
		RegistrationModule regModule = new RegistrationModule( m_userDAOBean );
		// Benutzer registrieren und QR-Code zurückgeben
		return regModule.registerUser( user ); // TODO Fehlerhandling
	}
}
