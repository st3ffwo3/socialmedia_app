package edu.hm.sisy.ssma.api.object.resource.error;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * Resource für einen Validierungsfehler.
 * 
 * @author Stefan Wörner
 */
@JsonPropertyOrder( value = { "message", "details" }, alphabetic = true )
@JsonSerialize( include = Inclusion.NON_NULL )
@Produces( { MediaType.APPLICATION_JSON } )
@Consumes( { MediaType.APPLICATION_JSON } )
public class ValidationErrorResponse extends ErrorResponse
{

	private static final long serialVersionUID = 6509060267616391158L;

	/**
	 * Standard Fehlermeldung.
	 */
	public static final String DEFAULT_ERROR_MESSAGE = "Die Validierung der Daten ist fehlgeschlagen.";

	private List<String> m_validationMessages = new ArrayList<String>();

	/**
	 * Liefert das Attribut validationMessages.
	 * 
	 * @return validationMessages
	 */
	@JsonProperty( "details" )
	public List<String> getValidationMessages()
	{
		return m_validationMessages;
	}

	/**
	 * Setzt das Attribut validationMessages.
	 * 
	 * @param validationMessages
	 *            zu setzender Wert für das Attribut validationMessages
	 */
	@JsonProperty( "details" )
	public void setValidationMessages( List<String> validationMessages )
	{
		m_validationMessages = validationMessages;
	}
}
