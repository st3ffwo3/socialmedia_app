package edu.hm.sisy.ssma.internal.interceptor;

import java.lang.annotation.Annotation;

import javax.ejb.EJB;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.core.Context;

import org.apache.commons.lang3.StringUtils;

import edu.hm.sisy.ssma.api.communication.request.IAuthenticationService;

/**
 * Interceptor Klasse prüft die Authentifizierung vor dem Methodenaufruf.
 * 
 * @author Stefan Wörner
 */
@Interceptor
public class AuthenticationInterceptor
{

	@EJB
	private IAuthenticationService m_authenticationService;

	/**
	 * Interceptor Methode führt Benutzerauthentifizierung durch und wirft bei Fehlschlag Exception.
	 * 
	 * @param ctx
	 *            Interception Context
	 * @return proceed object for call chain
	 * @throws Exception
	 *             Exception
	 */
	@AroundInvoke
	public Object authenticate( InvocationContext ctx ) throws Exception
	{
		Object[] params = ctx.getParameters();
		Annotation[][] paramsAnnotations = getMethodParamAnnotationsInInterface( ctx );

		HttpServletResponse response = getResponseObject( params, paramsAnnotations );
		String ssmsToken = getSsmsToken( params, paramsAnnotations );

		// Authentifizierung durchführen
		// => Bei fehlerhafter Authentifizierung wird Exception geworfen und weitere Verarbeitung abgebrochen
		m_authenticationService.login( null, ssmsToken, response );

		// Aufgerufene Methode ausführen (Auth ok)
		return ctx.proceed();
	}

	private Annotation[][] getMethodParamAnnotationsInInterface( InvocationContext ctx )
	{
		for (Class<?> interfaceClass : ctx.getMethod().getDeclaringClass().getInterfaces())
		{
			try
			{
				return interfaceClass.getMethod( ctx.getMethod().getName(), ctx.getMethod().getParameterTypes() )
						.getParameterAnnotations();
			}
			catch (Exception e)
			{
				continue;
			}
		}

		return null;
	}

	private HttpServletResponse getResponseObject( Object[] params, Annotation[][] paramsAnnotations )
	{
		if (params == null || paramsAnnotations == null)
		{
			return null;
		}

		for (int i = 0; i < params.length; i++)
		{
			if (params[i] instanceof HttpServletResponse)
			{
				for (Annotation annotation : paramsAnnotations[i])
				{
					if (annotation instanceof Context)
					{
						return (HttpServletResponse) params[i];
					}
				}
				continue;
			}
		}

		return null;
	}

	private String getSsmsToken( Object[] params, Annotation[][] paramsAnnotations )
	{
		if (params == null || paramsAnnotations == null)
		{
			return null;
		}

		for (int i = 0; i < params.length; i++)
		{
			if (params[i] instanceof String)
			{
				for (Annotation annotation : paramsAnnotations[i])
				{
					if (annotation instanceof HeaderParam
							&& StringUtils.equalsIgnoreCase( ((HeaderParam) annotation).value(), "ssms-token" ))
					{
						return (String) params[i];
					}
				}
				continue;
			}
		}

		return null;
	}
}
