package edu.hm.sisy.ssma.service.provider;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import edu.hm.sisy.ssma.api.object.resource.error.BasicError;
import edu.hm.sisy.ssma.internal.object.exception.IllegalUserUpdateException;

/**
 * Provider mappt eine IllegalUserUpdateException in einen entsprechenden HTTP Fehlercode inkl. Fehlermeldung im
 * Content.
 * 
 * @author Stefan Wörner
 */
@Provider
public class IllegalUserUpdateExceptionMapper implements ExceptionMapper<IllegalUserUpdateException>
{

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
	 */
	public Response toResponse( IllegalUserUpdateException iuuex )
	{
		BasicError error = new BasicError();
		error.getMessages().add( iuuex.getMessage() );

		// HTTP Fehlercode 400 := Bad Request
		return Response.status( 400 ).entity( error ).build();
	}
}
