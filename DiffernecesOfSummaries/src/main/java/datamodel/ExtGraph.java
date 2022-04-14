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
import java.util.Objects;
import java.util.Optional;


public class ExtGraph {

    private static final Logger logger = LogManager.getLogger(ExtGraph.class);

    private final String name;
    private final MultiGraph graph;
    private HashMap<Integer, Integer> secondaryIndex;
    private Graph<Integer, Edge> distinctGraph;
    private final HashMap<MetricTypes, Float> unaryResults;
    private final HashMap<MetricTypes, Long> unaryCompTimes;
    private final HashMap<MetricTypes, Float> binaryResults;
    private final HashMap<MetricTypes, Long> binaryCompTimes;

    public ExtGraph(String name, MultiGraph graph, Graph<Integer, Edge> distinctGraph, String path2secondaryIndex) {
        this.name = name;
        this.graph = graph;
        this.distinctGraph = distinctGraph;
        try {
            this.secondaryIndex = Objects.requireNonNull(SecondaryIndex.readFromJson(path2secondaryIndex)).getSchemaElementToImprint();
        } catch (NullPointerException e) {
            logger.error("Couldnt read SecondaryIndex for " + name);
        }
        unaryResults = new HashMap<>();
        unaryCompTimes = new HashMap<>();
        binaryResults = new HashMap<>();
        binaryCompTimes = new HashMap<>();
    }

    public ExtGraph() {
        this.name = "null";
        this.graph = new MultiGraph();
        this.distinctGraph = new DirectedMultigraph<>(Edge.class);
        this.secondaryIndex = new HashMap<>();
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

    public MultiGraph getGraph() {
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

    public static ExtGraph[] createGraphs(List<Optional<ODatabaseSession>> sessionList) {
        ExtGraph[] extGraphs = new ExtGraph[sessionList.size()];
        long start = System.currentTimeMillis();
        for (int i = 0; i < extGraphs.length; i++) {
            if (sessionList.get(i).isEmpty()) {
                extGraphs[i] = new ExtGraph();
                continue;
            }
            ODatabaseSession session = sessionList.get(i).get();
            System.out.println("[Graph] Creating Graph " + session.getName());
            MultiGraph graph = new MultiGraph();
            DirectedMultigraph<Integer, Edge> distinctGraph = new DirectedMultigraph<>(Edge.class);
            List<Integer> vertexList = Queries.getVertices(session).orElseThrow(() -> new ODatabaseException("Couldn't Fetch Vertices"));
            for (Integer v : vertexList) {
                graph.addVertex(v);
                distinctGraph.addVertex(v);
            }
            System.out.println("[Graph] VertexList Size: " + vertexList.size());
            List<Edge> edgeList = null;
            try {
                edgeList = Queries.getEdges(session).orElseThrow(() -> new ODatabaseException("Couldn't Fetch Edges"));
            } catch (Exception e) {
                System.out.println("[Graph] Couldn't Queary Edges");
            }
            if (edgeList == null) continue;
            System.out.println("[Graph] Edgelist Size: " + edgeList.size());

            for (Edge e : edgeList) {
                try {
                    graph.addEdge(e.getIn(), e.getOut(), e);
                    distinctGraph.addEdge(e.getIn(), e.getOut(), e);
                } catch (Exception exe) {
                    System.out.println("[Graph] Coudln't create Edge");
                }
            }
            System.out.println("[Graph] Edges in Graph: " + graph.edgeSet().size());

            extGraphs[i] = new ExtGraph(session.getName(), graph,distinctGraph, "/media/nvme7n1/jmuecke/TemporalGraphDifferences/DiffernecesOfSummaries/Indicies/" + session.getName() + ".json");
            extGraphs[i].getUnaryCompTimes().put(MetricTypes.GRPAH_CREATION, System.currentTimeMillis() - start);
        }
        return extGraphs;
    }

    public void computeUnaryMetrics(UnaryMetric[] metrics) {
        for (UnaryMetric metric : metrics) {
            Result result = metric.compute(this);
            if (result != null) {
                unaryResults.put(result.getMetric(), result.getDifference());
                unaryCompTimes.put(result.getMetric(), result.getCompTime());
            }
        }
    }

    public void computeBinaryMetrics(BinaryMetric[] metrics, ExtGraph referenceGraph) {
        for (BinaryMetric metric : metrics) {
            Result result = metric.compute(referenceGraph, this);
            if (result != null) {
                binaryResults.put(result.getMetric(), result.getDifference());
                binaryCompTimes.put(result.getMetric(), result.getCompTime());
            }
        }
    }

    public Graph<Integer, Edge> getDistinctGraph() {
        return distinctGraph;
    }
}
