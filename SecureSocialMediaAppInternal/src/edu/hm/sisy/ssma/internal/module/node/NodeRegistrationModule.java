package edu.hm.sisy.ssma.internal.module.node;

import edu.hm.sisy.ssma.internal.bean.database.INodeDAOLocal;
import edu.hm.sisy.ssma.internal.object.entity.EntityNode;

/**
 * Registrierungsmodul für die Node-Registrierung.
 * 
 * @author Stefan Wörner
 */
public class NodeRegistrationModule
{

	private INodeDAOLocal m_nodeDAOBean;

	/**
	 * Standardkonstruktor.
	 * 
	 * @param nodeDAOBean
	 *            Referenz auf Node DAO Bean
	 */
	public NodeRegistrationModule( INodeDAOLocal nodeDAOBean )
	{
		m_nodeDAOBean = nodeDAOBean;
	}

	/**
	 * Registriert einen Node im Cluster/System.
	 * 
	 * @param nodeAddress
	 *            Zu registrierender Node
	 */
	public void register( String nodeAddress )
	{
		// Node Entität erzeugen
		EntityNode node = new EntityNode();
		node.setAddress( nodeAddress );

		// Node in der Datenbank speichern
		m_nodeDAOBean.createOrUpdate( node );
	}
}
