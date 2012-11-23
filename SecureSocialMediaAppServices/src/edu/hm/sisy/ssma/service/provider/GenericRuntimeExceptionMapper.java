package edu.hm.sisy.ssma.service.provider;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.Providers;

import edu.hm.sisy.ssma.api.object.resource.error.BaseError;

/**
 * Provider mappt eine RuntimeException in einen entsprechenden HTTP Fehlercode inkl. Fehlermeldung im Content.
 * 
 * @author Stefan Wörner
 */
@Provider
public class GenericRuntimeExceptionMapper implements ExceptionMapper<RuntimeException>
{

	@Context
	private Providers m_providers;

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
	 */
	@SuppressWarnings( { "rawtypes", "unchecked" } )
	@Override
	public Response toResponse( RuntimeException ex )
	{
		ExceptionMapper mapper = null;
		Throwable t = ex;

		while (t.getCause() != null)
		{
			t = t.getCause();
			mapper = m_providers.getExceptionMapper( t.getClass() );

			if (mapper != null)
			{
				return mapper.toResponse( ex.getCause() );
			}
		}

		BaseError error = new BaseError();
		error.getMessages().add( ex.getMessage() );

		// HTTP Fehlercode 500 := Internal Server Error
		return Response.status( 500 ).entity( error ).build();
	}
}
