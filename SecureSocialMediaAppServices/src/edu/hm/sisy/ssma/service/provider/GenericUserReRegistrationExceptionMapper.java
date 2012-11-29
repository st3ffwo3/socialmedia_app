package edu.hm.sisy.ssma.service.provider;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import edu.hm.sisy.ssma.api.object.resource.error.BasicError;
import edu.hm.sisy.ssma.internal.object.exception.GenericUserReRegistrationException;

/**
 * Provider mappt eine GenericUserReRegistrationException in einen entsprechenden HTTP Fehlercode inkl. Fehlermeldung im
 * Content.
 * 
 * @author Stefan Wörner
 */
@Provider
public class GenericUserReRegistrationExceptionMapper implements ExceptionMapper<GenericUserReRegistrationException>
{

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
	 */
	public Response toResponse( GenericUserReRegistrationException urex )
	{
		BasicError error = new BasicError();
		error.getMessages().add( urex.getMessage() );

		// HTTP Fehlercode 500 := Internal Server Error
		return Response.status( 500 ).entity( error ).build();
	}
}
