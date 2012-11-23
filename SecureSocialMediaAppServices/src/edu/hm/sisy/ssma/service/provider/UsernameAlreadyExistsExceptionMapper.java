package edu.hm.sisy.ssma.service.provider;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import edu.hm.sisy.ssma.api.object.resource.error.ValidationErrorResponse;
import edu.hm.sisy.ssma.internal.object.exception.UsernameAlreadyExistsException;

/**
 * Provider mappt eine UsernameAlreadyExistsException in einen entsprechenden HTTP Fehlercode inkl. Fehlermeldung im
 * Content.
 * 
 * @author Stefan Wörner
 */
@Provider
public class UsernameAlreadyExistsExceptionMapper implements ExceptionMapper<UsernameAlreadyExistsException>
{

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
	 */
	public Response toResponse( UsernameAlreadyExistsException uaeex )
	{
		ValidationErrorResponse error = new ValidationErrorResponse();
		error.setMessage( ValidationErrorResponse.DEFAULT_ERROR_MESSAGE );
		error.getValidationMessages().add( uaeex.getMessage() );

		// HTTP Fehlercode 400 := Bad Request
		return Response.status( 400 ).entity( error ).build();
	}
}
