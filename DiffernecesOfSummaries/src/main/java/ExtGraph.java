import datamodel.SecondaryIndex;

import metrics.Metric;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jgrapht.Graph;
import results.Result;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;




public class ExtGraph {

    private static final Logger logger = LogManager.getLogger(ExtGraph.class);

    private final String name;
    private final Graph<Integer, Integer> graph;
    private HashMap<Integer, Integer[]> secondaryIndex;
    private final List<Result> results;

    public ExtGraph(String name, Graph<Integer, Integer> graph, String path2secondaryIndex) {
        this.name = name;
        this.graph = graph;
        try {
            this.secondaryIndex = SecondaryIndex.readFromJson(path2secondaryIndex).getSchemaElementToImprint();
        }
        catch(NullPointerException e){
            logger.warn("Failed to read secondaryIndex");
        }
        results = new LinkedList<>();

    }

    public List<Result> getResults() {
        return results;
    }

    public void computeMetrics(List<Metric> metrics){
        for(Metric metric : metrics) {
            results.add(metric.compute(this.graph, this.secondaryIndex, this.name));
        }
    }







}
