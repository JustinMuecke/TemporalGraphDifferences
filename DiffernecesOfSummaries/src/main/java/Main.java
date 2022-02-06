import com.orientechnologies.orient.core.db.ODatabaseSession;

import com.orientechnologies.orient.core.exception.ODatabaseException;
import metrics.Metric;
import metrics.unary.*;
import network.DBConnectionFailedException;
import network.DBConnector;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import network.FileWriter;
import network.Queries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jgrapht.Graph;
import org.jgrapht.graph.Multigraph;
import results.Result;


public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    private static List<ODatabaseSession> getDatabaseSession(String dbName) throws DBConnectionFailedException{
        return DBConnector.getDatabaseSessions(dbName).orElseThrow(() -> new DBConnectionFailedException("List of Sessions Empty"));
    }

    private static HashMap<String, ExtGraph> createGraphs(List<ODatabaseSession> sessionList){
        HashMap<String, ExtGraph> graphMap = new HashMap<>();
        for(ODatabaseSession session : sessionList) {
            Graph<Integer, Integer> graph = new Multigraph<>(Integer.class);
            List<Integer> vertexList = Queries.getVertices(session).orElseThrow(() -> new ODatabaseException("Couldn't Fetch Vertices"));
            for(Integer v : vertexList)
                graph.addVertex(v);

            graphMap.put(session.getName(), new ExtGraph(session.getName(), graph, "Indicies/" + session.getName() + "json"));
        }
        return graphMap;
    }

    private static List<Metric> createMetricList(){
        List<Metric> metricList = new LinkedList<>();
        metricList.add(new TMH());
        metricList.add(new NumberOfEQClasses());
        metricList.add(new Comp());
        metricList.add(new AvgSizeOfEQClass());
        metricList.add(new AvgNumberOfEdges());
        return metricList;

    }

    public static void main(String[] args)  {

        List<ODatabaseSession> sessionList;
        HashMap<String, ExtGraph> graphMap = null;
        List<Metric> metricList = createMetricList();
        try {
            sessionList = getDatabaseSession("justin-imp-test");
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
            for(Result<Long> r : g.getResults()){
                FileWriter.writeToFile(r);

            }
        }



    }
}
