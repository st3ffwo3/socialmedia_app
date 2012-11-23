package edu.hm.sisy.ssma.internal.bean.database;

import java.util.List;

import javax.ejb.Local;

import edu.hm.sisy.ssma.internal.object.entity.EntityNode;

/**
 * Interface der Bean für den Datenbankzugriff auf die Node Entität.
 * 
 * @author Stefan Wörner
 */
@Local
public interface INodeDAOLocal
{

	/**
	 * Estellt oder aktualisiert die Infos zu einem Node im Cluster/System in der Datenbank.
	 * 
	 * @param node
	 *            Zu erstellender/aktualisierender Node
	 * @return Erstellter/Aktualisierter Node
	 */
	EntityNode createOrUpdate( EntityNode node );

	/**
	 * Liest einen Node im Cluster/System anhand der Adresse aus der Datenbank aus.
	 * 
	 * @param address
	 *            Adresse des Node
	 * @return Gefundener Node oder NULL
	 */
	EntityNode read( String address );

	/**
	 * Liest alle Nodes im Cluster/System aus der Datenbank aus.
	 * 
	 * @return Liste mit gefundene Nodes oder leere Liste
	 */
	List<EntityNode> readAll();
}
