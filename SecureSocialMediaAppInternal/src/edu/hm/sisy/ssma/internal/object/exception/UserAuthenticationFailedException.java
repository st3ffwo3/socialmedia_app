package edu.hm.sisy.ssma.internal.object.exception;

import edu.hm.basic.exception.AbstractBasicRuntimeException;
import edu.hm.sisy.ssma.api.object.ErrorConstants;

/**
 * Exception wird geworfen wenn ein unsicheres Benutzerpasswort erkannt wurde.
 * 
 * @author Stefan Wörner
 */
public class UserAuthenticationFailedException extends AbstractBasicRuntimeException
{

	private static final long serialVersionUID = -1700889152187287375L;

	/**
	 * Standardkonstruktor.
	 */
	public UserAuthenticationFailedException()
	{
		super( ErrorConstants.USER_AUTHENTICATION_FAILED_ERROR_MSG );
	}
}
