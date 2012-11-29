package edu.hm.sisy.ssma.internal.object.exception;

import edu.hm.basic.exception.AbstractBasicRuntimeException;
import edu.hm.sisy.ssma.api.object.ErrorConstants;

/**
 * Exception wird geworfen wenn bei der Benutzeraktualisierung ein allgemeiner Fehler erkannt wurde.
 * 
 * @author Stefan Wörner
 */
public class GenericUserUpdateException extends AbstractBasicRuntimeException
{

	private static final long serialVersionUID = -9084186635079324896L;

	/**
	 * Standardkonstruktor.
	 */
	public GenericUserUpdateException()
	{
		super( ErrorConstants.GENERIC_USER_UPDATE_ERROR_MSG );
	}
}
