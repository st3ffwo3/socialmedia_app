package edu.hm.sisy.ssma.internal.object.query;

/**
 * Konstanten für Queries der Node Entität.
 * 
 * @author Stefan Wörner
 */
public final class NodeQueryConstants
{

	private NodeQueryConstants()
	{

	}

	/**
	 * Name der "Alle Nodes"-Query.
	 */
	public static final String GET_ALL = "GetAllNodesQuery";

	/**
	 * Alle Nodes.
	 */
	public static final String GET_ALL_QUERY = "SELECT n FROM EntityNode n";

}
