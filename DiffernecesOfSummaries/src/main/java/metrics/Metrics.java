package metrics;

import com.orientechnologies.orient.core.db.ODatabaseSession;
import network.Queries;
import results.AvgLinksResult;
import results.NumberOfEdgesResult;
import results.NumberOfNodesResult;

public class Metrics {

    public static AvgLinksResult changeInAverageLinks(ODatabaseSession session1, ODatabaseSession session2){
        float avgLinksOne = (float) Queries.getNumberOfEdges(session1) / Queries.getNumberOfVertices(session1);
        String name1 = session1.getName();
        float avgLinksTwo = (float) Queries.getNumberOfEdges(session2) / Queries.getNumberOfVertices(session2);
        String name2 = session2.getName();
        return new AvgLinksResult(name1, name2,avgLinksTwo - avgLinksOne);
    }

    public static NumberOfNodesResult changeInNumberOfNOdes(ODatabaseSession session1, ODatabaseSession session2){
        int numberOfVerticesDB1 = (int) Queries.getNumberOfVertices(session1);
        String name1 = session1.getName();
        int numberOfVerticesDB2 = (int) Queries.getNumberOfVertices(session2);
        String name2 = session2.getName();
        return new NumberOfNodesResult(name1, name2,numberOfVerticesDB2 - numberOfVerticesDB1);
    }

    public static NumberOfEdgesResult changeInNumberOfEdges(ODatabaseSession session1, ODatabaseSession session2){
        int numberOfEdgesDB1 = (int) Queries.getNumberOfEdges(session1);
        String name1 = session1.getName();
        int numberOfEdgesDB2 = (int) Queries.getNumberOfEdges(session2);
        String name2 = session2.getName();
        return new NumberOfEdgesResult(name1, name2, numberOfEdgesDB2 - numberOfEdgesDB1);
    }
}
