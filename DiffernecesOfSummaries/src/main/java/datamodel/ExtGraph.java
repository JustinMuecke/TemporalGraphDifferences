package datamodel;

import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.exception.ODatabaseException;
import metrics.BinaryMetric;
import metrics.UnaryMetric;
import network.Queries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jgrapht.Graph;
import org.jgrapht.graph.DirectedMultigraph;
import results.MetricTypes;
import results.Result;

import java.util.HashMap;
import java.util.List;


public class ExtGraph {

    private static final Logger logger = LogManager.getLogger(ExtGraph.class);

    private final String name;
    private final Graph<Integer, Edge> graph;
    private HashMap<Integer, Integer> secondaryIndex;
    private final HashMap<MetricTypes, Float> unaryResults;
    private final HashMap<MetricTypes, Long> unaryCompTimes;
    private final HashMap<MetricTypes, Float> binaryResults;
    private final HashMap<MetricTypes, Long> binaryCompTimes;

    public ExtGraph(String name, Graph<Integer, Edge> graph, String path2secondaryIndex) {
    	 this.name = name;
        this.graph = graph;
        try {
            this.secondaryIndex = SecondaryIndex.readFromJson(path2secondaryIndex).getSchemaElementToImprint();
        }
        catch(NullPointerException e){
            logger.error("Couldnt read SecondaryIndex for " + name);
        }
        unaryResults = new HashMap<>();
        unaryCompTimes = new HashMap<>();
        binaryResults = new HashMap<>();
        binaryCompTimes = new HashMap<>();

    }

    public HashMap<MetricTypes, Float> getUnaryResults() {
        return unaryResults;
    }

    public String getName() {
        return name;
    }

    public Graph<Integer, Edge> getGraph() {
        return graph;
    }

    public HashMap<Integer, Integer> getSecondaryIndex() {
        return secondaryIndex;
    }

    public HashMap<MetricTypes, Long> getUnaryCompTimes() {
        return unaryCompTimes;
    }

    public HashMap<MetricTypes, Float> getBinaryResults() {
        return binaryResults;
    }

    public HashMap<MetricTypes, Long> getBinaryCompTimes() {
        return binaryCompTimes;
    }

    public static ExtGraph[] createGraphs(List<ODatabaseSession> sessionList){
        ExtGraph[] extGraphs = new ExtGraph[sessionList.size()];
        long start = System.currentTimeMillis();
        for(int i = 0; i < extGraphs.length; i++) {
            Graph<Integer, Edge> graph = new DirectedMultigraph<>(Edge.class);
            List<Integer> vertexList = Queries.getVertices(sessionList.get(i)).orElseThrow(() -> new ODatabaseException("Couldn't Fetch Vertices"));
            for(Integer v : vertexList)
                graph.addVertex(v);
            List<Edge> edgeList = Queries.getEdges(sessionList.get(i)).orElseThrow(() -> new ODatabaseException("Couldn't Fetch Edges"));
            for(Edge e : edgeList) {
                graph.addEdge(e.getIn(), e.getOut(), e);
            }
            extGraphs[i] = new ExtGraph(sessionList.get(i).getName(), graph, "/media/nvme7n1/jmuecke/TemporalGraphDifferences/DiffernecesOfSummaries/Indicies/" + sessionList.get(i).getName() + ".json");
            extGraphs[i].getUnaryCompTimes().put(MetricTypes.GRPAH_CREATION, System.currentTimeMillis() - start);
        }
        return extGraphs;
    }

    public void computeUnaryMetrics(UnaryMetric[] metrics){
        for (UnaryMetric metric : metrics) {
            Result result = metric.compute(this);
            if (result != null) {
                unaryResults.put(result.getMetric(), result.getDifference());
                unaryCompTimes.put(result.getMetric(), result.getCompTime());
            }
        }
    }

    public void computeBinaryMetrics(BinaryMetric[] metrics, ExtGraph referenceGraph){
        logger.info("Computing BinaryMetric between " + this.getName() + " and " + referenceGraph.getName());
        logger.info("Edge Size of Graph1: " + this.getGraph().edgeSet());
        logger.info("Edge Size of Graph2: " + this.getGraph().edgeSet());
        for(BinaryMetric metric : metrics){
            Result result = metric.compute(referenceGraph, this);
            if(result != null){
                binaryResults.put(result.getMetric(), result.getDifference());
                binaryCompTimes.put(result.getMetric(), result.getCompTime());
            }
        }
    }

}
