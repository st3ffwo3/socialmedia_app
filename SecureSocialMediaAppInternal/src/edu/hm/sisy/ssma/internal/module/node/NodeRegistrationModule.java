package edu.hm.sisy.ssma.internal.module.node;

import edu.hm.sisy.ssma.internal.bean.database.INodeDAOLocal;
import edu.hm.sisy.ssma.internal.object.entity.EntityNode;

/**
 * Registrierungsmodul für die Knoten-Registrierung.
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
	 * Registriert einen Knoten im Systemverbund.
	 * 
	 * @param nodeAddress
	 *            Zu registrierender Knoten
	 */
	public void register( String nodeAddress )
	{
		// Node Entität erzeugen
		EntityNode node = new EntityNode();
		node.setAddress( nodeAddress );

		// Knoten in der Datenbank speichern
		m_nodeDAOBean.createOrUpdate( node );
	}
}
