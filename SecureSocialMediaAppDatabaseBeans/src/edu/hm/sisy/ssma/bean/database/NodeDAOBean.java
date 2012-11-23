package edu.hm.sisy.ssma.bean.database;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import edu.hm.sisy.ssma.internal.bean.AbstractBean;
import edu.hm.sisy.ssma.internal.bean.database.INodeDAOLocal;
import edu.hm.sisy.ssma.internal.object.entity.EntityNode;
import edu.hm.sisy.ssma.internal.object.query.NodeQueryConstants;

/**
 * Bean für den Datenbankzugriff auf die Node Entität.
 * 
 * @author Stefan Wörner
 */
@Stateless
public class NodeDAOBean extends AbstractBean implements INodeDAOLocal
{

	@PersistenceContext( unitName = "SsmAppManager" )
	private EntityManager m_em;

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.sisy.ssma.internal.bean.database.INodeDAOLocal#createOrUpdate(edu.hm.sisy.ssma.internal.object.entity.EntityNode)
	 */
	@Override
	public EntityNode createOrUpdate( EntityNode node )
	{
		EntityNode nodeExisting = read( node.getAddress() );

		if (nodeExisting != null)
		{
			nodeExisting.setLastUpdated( new Date() );
			node = m_em.merge( nodeExisting );
		}
		else
		{
			node.setLastUpdated( new Date() );
			m_em.persist( node );
		}

		m_em.flush();
		return node;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.sisy.ssma.internal.bean.database.INodeDAOLocal#read(java.lang.String)
	 */
	@Override
	public EntityNode read( String address )
	{
		return m_em.find( EntityNode.class, address );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.sisy.ssma.internal.bean.database.INodeDAOLocal#readAll()
	 */
	@Override
	public List<EntityNode> readAll()
	{
		TypedQuery<EntityNode> query = m_em.createNamedQuery( NodeQueryConstants.GET_ALL, EntityNode.class );
		return query.getResultList();
	}
}
