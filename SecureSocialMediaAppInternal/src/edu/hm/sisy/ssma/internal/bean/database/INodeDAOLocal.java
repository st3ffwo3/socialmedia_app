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
	 * Estellt oder aktualisiert die Infos zu einem Knoten im Systemverbund in der Datenbank.
	 * 
	 * @param node
	 *            Zu erstellender/aktualisierender Knoten
	 * @return Erstellter/Aktualisierter Knoten
	 */
	EntityNode createOrUpdate( EntityNode node );

	/**
	 * Liest einen Knoten im Systemverbund anhand der Adresse aus der Datenbank aus.
	 * 
	 * @param address
	 *            Adresse des Knoten
	 * @return Gefundener Knoten oder NULL
	 */
	EntityNode read( String address );

	/**
	 * Liest alle Knoten im Systemverbund aus der Datenbank aus.
	 * 
	 * @return Liste mit gefundene Knoten oder leere Liste
	 */
	List<EntityNode> readAll();
}
