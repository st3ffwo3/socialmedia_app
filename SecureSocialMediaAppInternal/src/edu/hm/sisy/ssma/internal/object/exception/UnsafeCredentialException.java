package edu.hm.sisy.ssma.internal.object.exception;

import edu.hm.basic.exception.AbstractBasicRuntimeException;
import edu.hm.sisy.ssma.api.object.ErrorConstants;

/**
 * Exception wird geworfen wenn ein unsicheres Benutzerpasswort erkannt wurde.
 * 
 * @author Stefan Wörner
 */
public class UnsafeCredentialException extends AbstractBasicRuntimeException
{

	private static final long serialVersionUID = -3715059271192352117L;

	/**
	 * Standardkonstruktor.
	 */
	public UnsafeCredentialException()
	{
		super( ErrorConstants.USER_PASSWORD_UNSAFE_ERROR_MSG );
	}
}
