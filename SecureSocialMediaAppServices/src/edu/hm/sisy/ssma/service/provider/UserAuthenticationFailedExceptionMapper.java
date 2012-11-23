package edu.hm.sisy.ssma.service.provider;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import edu.hm.sisy.ssma.api.object.resource.error.BaseError;
import edu.hm.sisy.ssma.internal.object.exception.UserAuthenticationFailedException;

/**
 * Provider mappt eine UserAuthenticationFailedException in einen entsprechenden HTTP Fehlercode inkl. Fehlermeldung im
 * Content.
 * 
 * @author Stefan Wörner
 */
@Provider
public class UserAuthenticationFailedExceptionMapper implements ExceptionMapper<UserAuthenticationFailedException>
{

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
	 */
	public Response toResponse( UserAuthenticationFailedException uafex )
	{
		BaseError error = new BaseError();
		error.getMessages().add( uafex.getMessage() );

		// HTTP Fehlercode 400 := Bad Request
		return Response.status( 400 ).entity( error ).build();
	}
}
