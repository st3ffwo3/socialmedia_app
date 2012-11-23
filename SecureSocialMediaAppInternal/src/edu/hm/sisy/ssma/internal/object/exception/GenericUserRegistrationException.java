package edu.hm.sisy.ssma.internal.object.exception;

import edu.hm.basic.exception.AbstractBasicRuntimeException;
import edu.hm.sisy.ssma.api.object.ErrorConstants;

/**
 * Exception wird geworfen wenn bei der Benutzerregistrierung ein allgemeiner Fehler erkannt wurde.
 * 
 * @author Stefan Wörner
 */
public class GenericUserRegistrationException extends AbstractBasicRuntimeException
{

	private static final long serialVersionUID = -9084186635079324896L;

	/**
	 * Standardkonstruktor.
	 */
	public GenericUserRegistrationException()
	{
		super( ErrorConstants.GENERIC_USER_REGISTRATION_FAILURE_ERROR_MSG );
	}
}
