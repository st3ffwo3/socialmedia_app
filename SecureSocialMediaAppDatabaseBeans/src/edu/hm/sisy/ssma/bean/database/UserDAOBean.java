package edu.hm.sisy.ssma.bean.database;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import edu.hm.sisy.ssma.internal.bean.AbstractBean;
import edu.hm.sisy.ssma.internal.bean.database.IUserDAOLocal;
import edu.hm.sisy.ssma.internal.object.entity.EntityUser;

/**
 * Bean für den Datenbankzugriff auf die User Entität.
 * 
 * @author Stefan Wörner
 */
@Stateless
public class UserDAOBean extends AbstractBean implements IUserDAOLocal
{

	@PersistenceContext( unitName = "SsmAppManager" )
	private EntityManager em;

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.sisy.ssma.internal.bean.database.IUserDAOLocal#create(edu.hm.sisy.ssma.internal.object.entity.EntityUser)
	 */
	@Override
	public EntityUser create( EntityUser user )
	{
		em.persist( user );
		em.flush();
		return user;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.sisy.ssma.internal.bean.database.IUserDAOLocal#read(java.lang.String)
	 */
	@Override
	public EntityUser read( String userName )
	{
		return em.find( EntityUser.class, userName );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.sisy.ssma.internal.bean.database.IUserDAOLocal#update(edu.hm.sisy.ssma.internal.object.entity.EntityUser)
	 */
	@Override
	public EntityUser update( EntityUser user )
	{
		// TODO Logik
		user = em.merge( user );
		em.flush();
		return user;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.sisy.ssma.internal.bean.database.IUserDAOLocal#delete(edu.hm.sisy.ssma.internal.object.entity.EntityUser)
	 */
	@Override
	public void delete( EntityUser user )
	{
		em.remove( user );
		em.flush();
	}
}
