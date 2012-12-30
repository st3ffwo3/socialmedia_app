package edu.hm.sisy.ssma.internal.object.exception;

import edu.hm.basic.exception.AbstractBasicRuntimeException;
import edu.hm.sisy.ssma.api.object.ErrorConstants;

/**
 * Exception wird geworfen wenn beim Passwortwechsel das alte Passwort nicht stimmt.
 * 
 * @author Stefan Wörner
 */
public class OldPasswordNotMatchException extends AbstractBasicRuntimeException
{

	private static final long serialVersionUID = -9084186635079324896L;

	/**
	 * Standardkonstruktor.
	 */
	public OldPasswordNotMatchException()
	{
		super( ErrorConstants.ILLEGAL_OLD_PASSWORD_ERROR_MSG );
	}
}
