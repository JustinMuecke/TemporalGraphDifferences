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

import java.io.IOException;
import java.util.List;


public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    private static List<ODatabaseSession> getDatabaseSession(String dbName) throws DBConnectionFailedException{
        return DBConnector.getDatabaseSessions(dbName).orElseThrow(() -> new DBConnectionFailedException("List of Sessions Empty"));
    }

    private static ExtGraph[] createGraphs(List<ODatabaseSession> sessionList){
        ExtGraph[] extGraphs = new ExtGraph[sessionList.size()];
        for(int i = 0; i < extGraphs.length; i++) {
            Graph<Integer, Edge> graph = new DirectedMultigraph<>(Edge.class);
            List<Integer> vertexList = Queries.getVertices(sessionList.get(i)).orElseThrow(() -> new ODatabaseException("Couldn't Fetch Vertices"));
            for(Integer v : vertexList)
                graph.addVertex(v);
            List<Edge> edgeList = Queries.getEdges(sessionList.get(i)).orElseThrow(() -> new ODatabaseException("Couldn't Fetch Edges"));
            for(Edge e : edgeList) {
                graph.addEdge(e.getIn(), e.getOut(), e);
            }
             extGraphs[i] = new ExtGraph(sessionList.get(i).getName(), graph, "Indicies/" + sessionList.get(i).getName() + ".json");
        }
        return extGraphs;
    }

    private static Metric[] createMetricList(){
        return new Metric[]{new NumberOfEQClasses(), new AvgSizeOfEQClass(), new AvgNumberOfEdges(), new Comp(), new TMH()};
    }

    public static void main(String[] args)  {



        List<ODatabaseSession> sessionList;
        ExtGraph[] graphList = null;
        Metric[] metricList = createMetricList();
        try {
            sessionList = getDatabaseSession("justin-test");
            graphList = createGraphs(sessionList);
        } catch (DBConnectionFailedException e) {
            e.printStackTrace();
        }
        if(graphList == null){
            logger.error("Couldn't load Graphs");
            return;
        }
        for (ExtGraph extGraph : graphList) {
            extGraph.computeMetrics(metricList);
        }
        try {
            FileWriter.initializeUnaryCSVFile();
            for (ExtGraph g : graphList) {
                FileWriter.writeToUnaryFile(g.getResults(), g.getName());
            }
        } catch( IOException e){
            logger.error("Couldnt write to File");
        }



    }
}
