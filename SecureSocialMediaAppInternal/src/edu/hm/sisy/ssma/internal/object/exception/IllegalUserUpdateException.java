package edu.hm.sisy.ssma.internal.object.exception;

import edu.hm.basic.exception.AbstractBasicRuntimeException;
import edu.hm.sisy.ssma.api.object.ErrorConstants;

/**
 * Exception wird geworfen wenn unberechtigterweise die Benutzer-Credentials aktualisiert werden sollen.
 * 
 * @author Stefan Wörner
 */
public class IllegalUserUpdateException extends AbstractBasicRuntimeException
{

	private static final long serialVersionUID = -9084186635079324896L;

	/**
	 * Standardkonstruktor.
	 */
	public IllegalUserUpdateException()
	{
		super( ErrorConstants.ILLEGAL_USER_UPDATE_ERROR_MSG );
	}
}
