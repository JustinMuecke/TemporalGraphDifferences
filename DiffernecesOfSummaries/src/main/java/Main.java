import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.exception.ODatabaseException;
import datamodel.Edge;
import datamodel.ExtGraph;
import metrics.Metric;
import metrics.unary.*;
import network.DBConnectionFailedException;
import network.DBConnector;
import network.FileWriter;
import network.Queries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jgrapht.Graph;
import org.jgrapht.graph.DirectedMultigraph;
import results.MetricTypes;

import java.util.HashMap;
import java.util.List;


public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    private static List<ODatabaseSession> getDatabaseSession(String dbName) throws DBConnectionFailedException{
        return DBConnector.getDatabaseSessions(dbName).orElseThrow(() -> new DBConnectionFailedException("List of Sessions Empty"));
    }

    private static HashMap<String, ExtGraph> createGraphs(List<ODatabaseSession> sessionList){
        HashMap<String, ExtGraph> graphMap = new HashMap<>();
        for(ODatabaseSession session : sessionList) {
            Graph<Integer, Edge> graph = new DirectedMultigraph<>(Edge.class);
            List<Integer> vertexList = Queries.getVertices(session).orElseThrow(() -> new ODatabaseException("Couldn't Fetch Vertices"));
            for(Integer v : vertexList)
                graph.addVertex(v);
            List<Edge> edgeList = Queries.getEdges(session).orElseThrow(() -> new ODatabaseException("Couldn't Fetch Edges"));
            for(Edge e : edgeList) {
                graph.addEdge(e.getIn(), e.getOut(), e);
            }
            graphMap.put(session.getName(), new ExtGraph(session.getName(), graph, "Indicies/" + session.getName() + ".json"));
        }
        return graphMap;
    }

    private static Metric[] createMetricList(){
        return new Metric[]{new NumberOfEQClasses(), new AvgSizeOfEQClass(), new AvgNumberOfEdges(), new Comp(), new TMH()};
    }

    public static void main(String[] args)  {



        List<ODatabaseSession> sessionList;
        HashMap<String, ExtGraph> graphMap = null;
        Metric[] metricList = createMetricList();
        try {
            sessionList = getDatabaseSession("justin-test");
            graphMap = createGraphs(sessionList);


        } catch (DBConnectionFailedException e) {
            e.printStackTrace();
        }
        if(graphMap == null){
            logger.error("Couldn't load Graphs");
            return;
        }
        for(ExtGraph g : graphMap.values()){
            g.computeMetrics(metricList);
        }
        for(ExtGraph g : graphMap.values()){
            for(MetricTypes r : g.getResults().keySet()){
                FileWriter.writeToFile(g.getName(), r, g.getResults().get(r));

            }
        }



    }
}
