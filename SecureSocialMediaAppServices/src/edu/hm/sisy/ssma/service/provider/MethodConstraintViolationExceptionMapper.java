package edu.hm.sisy.ssma.service.provider;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.hibernate.validator.method.MethodConstraintViolation;
import org.hibernate.validator.method.MethodConstraintViolationException;

import edu.hm.sisy.ssma.api.object.resource.error.ValidationErrorResponse;

/**
 * Provider mappt eine MethodConstraintViolationException in einen entsprechenden HTTP Fehlercode inkl. Fehlermeldung im
 * Content.
 * 
 * @author Stefan Wörner
 */
@Provider
public class MethodConstraintViolationExceptionMapper implements ExceptionMapper<MethodConstraintViolationException>
{

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
	 */
	public Response toResponse( MethodConstraintViolationException cex )
	{
		ValidationErrorResponse error = new ValidationErrorResponse();
		error.setMessage( ValidationErrorResponse.DEFAULT_ERROR_MESSAGE );

		for (MethodConstraintViolation<?> violation : cex.getConstraintViolations())
		{
			error.getValidationMessages().add( violation.getMessage() );
		}

		// HTTP Fehlercode 400 := Bad Request
		return Response.status( 400 ).entity( error ).build();
	}
}
