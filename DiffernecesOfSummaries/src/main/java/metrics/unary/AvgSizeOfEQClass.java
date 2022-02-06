package metrics.unary;

import metrics.Metric;
import org.jgrapht.Graph;
import results.AvgSizeOfEQClassResult;

import java.util.HashMap;

public class AvgSizeOfEQClass implements Metric {


    @Override
    public AvgSizeOfEQClassResult compute(Graph<Integer, Integer> graph, HashMap<Integer, Integer[]> secondaryIndex, String name) {
        int sum = 0;
        for(Integer[] collected : secondaryIndex.values()){
            sum += collected.length;
        }
        float avg = (float) sum / graph.vertexSet().size();
        return new AvgSizeOfEQClassResult(name, avg);
    }
}
