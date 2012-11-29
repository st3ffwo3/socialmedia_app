package edu.hm.sisy.ssma.service.bean.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletResponse;

import edu.hm.sisy.ssma.api.communication.request.IAuthenticationService;
import edu.hm.sisy.ssma.api.communication.request.INodeRegistryService;
import edu.hm.sisy.ssma.api.object.resource.BasicNode;
import edu.hm.sisy.ssma.internal.bean.AbstractBean;
import edu.hm.sisy.ssma.internal.bean.database.INodeDAOLocal;
import edu.hm.sisy.ssma.internal.interceptor.AuthenticationInterceptor;
import edu.hm.sisy.ssma.internal.interceptor.LoggingInterceptor;
import edu.hm.sisy.ssma.internal.object.entity.EntityNode;

/**
 * REST-Service für die Node-Registry. Verfügbare Aktionen: GET
 * 
 * @author Stefan Wörner
 */
@Stateless
@Interceptors( { LoggingInterceptor.class, AuthenticationInterceptor.class } )
public class NodeRegistryService extends AbstractBean implements INodeRegistryService
{

	@EJB
	private INodeDAOLocal m_nodeDAOBean;

	@EJB
	private IAuthenticationService m_authenticationService;

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.sisy.ssma.api.communication.request.INodeRegistryService#findAll(java.lang.String,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public List<BasicNode> findAll( String ssmsToken, HttpServletResponse response )
	{
		// Alle Nodes aus der Datenbank auslesen
		List<EntityNode> eNodeList = m_nodeDAOBean.readAll();
		List<BasicNode> nodeList = new ArrayList<BasicNode>();

		// Für jeden EntityNode einen BasicNode erzeugen
		for (EntityNode eNode : eNodeList)
		{
			// BasicNode erzeugen
			BasicNode node = new BasicNode();
			node.setAddress( eNode.getAddress() );

			// Erstellten BasicNode der Liste hinzufügen
			nodeList.add( node );
		}

		// Liste mit Nodes zurückgeben
		return nodeList;
	}
}
