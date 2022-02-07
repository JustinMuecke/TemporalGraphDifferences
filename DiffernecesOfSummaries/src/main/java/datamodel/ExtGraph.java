package datamodel;

import metrics.Metric;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jgrapht.Graph;
import results.MetricTypes;
import results.Result;

import java.util.HashMap;




public class ExtGraph {

    private static final Logger logger = LogManager.getLogger(ExtGraph.class);

    private final String name;
    private final Graph<Integer, Edge> graph;
    private HashMap<Integer, Integer[]> secondaryIndex;
    private final HashMap<MetricTypes, Float> results;

    public ExtGraph(String name, Graph<Integer, Edge> graph, String path2secondaryIndex) {
        this.name = name;
        this.graph = graph;
        try {
            logger.info("PATH TO SECONDADRY: " + path2secondaryIndex);
            this.secondaryIndex = SecondaryIndex.readFromJson(path2secondaryIndex).getSchemaElementToImprint();
        }
        catch(NullPointerException e){
            logger.warn("Failed to read secondaryIndex");
        }
        results = new HashMap<MetricTypes, Float>(
        );

    }

    public HashMap<MetricTypes, Float> getResults() {
        return results;
    }

    public String getName() {
        return name;
    }

    public Graph<Integer, Edge> getGraph() {
        return graph;
    }

    public HashMap<Integer, Integer[]> getSecondaryIndex() {
        return secondaryIndex;
    }

    public void computeMetrics(Metric[] metrics){
        for(int i = 0; i < metrics.length; i++) {
            Result result = metrics[i].compute(this);
            if(result!= null) {
                results.put(result.getMetric(), result.getDifference());
            }
        }
    }







}
