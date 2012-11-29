package edu.hm.sisy.ssma.internal.object.exception;

import edu.hm.basic.exception.AbstractBasicRuntimeException;
import edu.hm.sisy.ssma.api.object.ErrorConstants;

/**
 * Exception wird geworfen wenn bei der Benutzer-Re-Registrierung ein allgemeiner Fehler erkannt wurde.
 * 
 * @author Stefan Wörner
 */
public class GenericUserReRegistrationException extends AbstractBasicRuntimeException
{

	private static final long serialVersionUID = -9084186635079324896L;

	/**
	 * Standardkonstruktor.
	 */
	public GenericUserReRegistrationException()
	{
		super( ErrorConstants.GENERIC_USER_RE_REGISTRATION_FAILURE_ERROR_MSG );
	}
}
