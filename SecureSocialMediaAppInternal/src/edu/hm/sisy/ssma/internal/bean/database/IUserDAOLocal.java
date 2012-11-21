package edu.hm.sisy.ssma.internal.bean.database;

import javax.ejb.Local;

import edu.hm.sisy.ssma.internal.object.entity.EntityUser;

/**
 * Interface der Bean für den Datenbankzugriff auf die User Entität.
 * 
 * @author Stefan Wörner
 */
@Local
public interface IUserDAOLocal
{

	/**
	 * Estellt einen neuen Benutzer in der Datenbank.
	 * 
	 * @param user
	 *            Zu erstellender Benutzer
	 * @return Erstellter Benutzer
	 */
	EntityUser create( EntityUser user );

	/**
	 * Liest einen Benutzer anhand des Benutzernamens aus der Datenbank aus.
	 * 
	 * @param userName
	 *            Benutzername
	 * @return Gefundener Benutzer oder NULL
	 */
	EntityUser read( String userName );

	/**
	 * Aktualisiert einen Benutzer in der Datenbank.
	 * 
	 * @param user
	 *            Zu aktualisierender Benutzer
	 * @return Aktualiserter Benutzer
	 */
	EntityUser update( EntityUser user );

	/**
	 * Entfernt einen Benutzer aus der Datenbank.
	 * 
	 * @param user
	 *            Zu entfernender Benutzer
	 */
	void delete( EntityUser user );
}
