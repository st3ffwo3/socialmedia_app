package edu.hm.sisy.ssma.internal.object.exception;

import edu.hm.basic.exception.AbstractBasicRuntimeException;
import edu.hm.sisy.ssma.api.object.ErrorConstants;

/**
 * Exception wird geworfen wenn eine Registrierung mit einem bereits vorhandenen Benutzernamen erfolgt.
 * 
 * @author Stefan Wörner
 */
public class UsernameAlreadyExistsException extends AbstractBasicRuntimeException
{

	private static final long serialVersionUID = -1895376174413716957L;

	/**
	 * Standardkonstruktor.
	 */
	public UsernameAlreadyExistsException()
	{
		super( ErrorConstants.USER_NAME_EXISTS_ERROR_MSG );
	}
}
