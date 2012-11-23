package edu.hm.sisy.ssma.service.provider;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import edu.hm.sisy.ssma.api.object.resource.error.ErrorResponse;
import edu.hm.sisy.ssma.internal.object.exception.UserRegistrationException;

/**
 * Provider mappt eine UnsafeCredentialException in einen entsprechenden HTTP Fehlercode inkl. Fehlermeldung im Content.
 * 
 * @author Stefan Wörner
 */
@Provider
public class UserRegistrationExceptionMapper implements ExceptionMapper<UserRegistrationException>
{

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
	 */
	public Response toResponse( UserRegistrationException urex )
	{
		ErrorResponse error = new ErrorResponse();
		error.setMessage( urex.getMessage() );

		// HTTP Fehlercode 500 := Internal Server Error
		return Response.status( 500 ).entity( error ).build();
	}
}
