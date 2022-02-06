import com.google.gson.Gson;
import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.record.OEdge;
import datamodel.Tuple;
import network.DBConnectionFailedException;
import network.DBConnector;
import network.Queries;
import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleGraph;

import java.util.List;


public class Main {

    private static Gson gson = new Gson();
    private List<Graph> graphList;

    public static void main(String[] args)  {
       Graph<Tuple<Integer, Integer>, OEdge> g = new SimpleGraph<>(OEdge.class);


        List<ODatabaseSession> sessionList;
        try {
            sessionList = DBConnector.getDatabaseSessions("justin-test").orElseThrow(() -> new DBConnectionFailedException("List of Sessions Empty"));
            List<Integer> vertices = Queries.getVertices(sessionList.get(0)).orElseThrow(() -> new DBConnectionFailedException("Couldnt get Vertices"));
            System.out.println(vertices.toString());
        } catch (DBConnectionFailedException e) {
            e.printStackTrace();
        }



    }
}
