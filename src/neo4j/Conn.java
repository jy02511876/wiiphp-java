package neo4j;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class Conn {

	
	private static enum RelTypes implements RelationshipType
	{
	    KNOWS
	}
	
	GraphDatabaseService graphDb;
	Node firstNode;
	Node secondNode;
	Relationship relationship;
	
	public void run()
	{
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( "http://master:7474/db/data" );
		Node node = graphDb.createNode();
		node.setProperty("name", "king");
		System.out.println(node.getProperty("name"));
		registerShutdownHook( graphDb );
	}
	


	private static void registerShutdownHook(final GraphDatabaseService graphDb) {
		// TODO Auto-generated method stub
		Runtime.getRuntime().addShutdownHook( new Thread()
	    {
	        @Override
	        public void run()
	        {
	            graphDb.shutdown();
	        }
	    } );
	}
	
	
	public static void main(String[] args){
		Conn conn = new Conn();
		conn.run();
		System.out.println("finished");
	}
}
