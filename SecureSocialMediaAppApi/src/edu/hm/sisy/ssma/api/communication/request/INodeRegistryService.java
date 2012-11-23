package edu.hm.sisy.ssma.api.communication.request;

import java.util.List;

import javax.ejb.Local;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.spi.validation.ValidateRequest;

import edu.hm.sisy.ssma.api.object.resource.BasicNode;

/**
 * REST-Service für die Node-Registry. Verfügbare Aktionen: GET
 * 
 * @author Stefan Wörner
 */
@ValidateRequest
@Local
@Path( "/node" )
@Produces( { MediaType.APPLICATION_JSON } )
@Consumes( { MediaType.APPLICATION_JSON } )
public interface INodeRegistryService
{

	/**
	 * Liefert eine Liste mit allem im Cluster/System vorhandenen Nodes zurück.
	 * 
	 * @param ssmsToken
	 *            Base64 codiertes SSMS-Token (Bestehend aus Nutzername und SessionToken)
	 * @param response
	 *            HttpServletResponse (wird verwendet um in den Response Header zu schreiben)
	 * @return Liste mit Nodes im Cluster/Systems.
	 */
	@GET
	@Path( "" )
	List<BasicNode> findAll( @HeaderParam( "ssms-token" ) String ssmsToken, @Context HttpServletResponse response );
}
