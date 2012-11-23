package edu.hm.sisy.ssma.bean.database;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import edu.hm.sisy.ssma.internal.bean.AbstractBean;
import edu.hm.sisy.ssma.internal.bean.database.IUserDAOLocal;
import edu.hm.sisy.ssma.internal.object.entity.EntityUser;
import edu.hm.sisy.ssma.internal.object.exception.UsernameAlreadyExistsException;

/**
 * Bean für den Datenbankzugriff auf die User Entität.
 * 
 * @author Stefan Wörner
 */
@Stateless
public class UserDAOBean extends AbstractBean implements IUserDAOLocal
{

	@PersistenceContext( unitName = "SsmAppManager" )
	private EntityManager m_em;

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.sisy.ssma.internal.bean.database.IUserDAOLocal#create(edu.hm.sisy.ssma.internal.object.entity.EntityUser)
	 */
	@Override
	public EntityUser create( EntityUser user )
	{
		if (read( user.getUsername() ) != null)
		{
			throw new UsernameAlreadyExistsException();
		}

		m_em.persist( user );
		m_em.flush();
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
		return m_em.find( EntityUser.class, userName );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.sisy.ssma.internal.bean.database.IUserDAOLocal#update(edu.hm.sisy.ssma.internal.object.entity.EntityUser)
	 */
	@Override
	public EntityUser update( EntityUser user )
	{
		user = m_em.merge( user );
		m_em.flush();
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
		m_em.remove( user );
		m_em.flush();
	}
}
