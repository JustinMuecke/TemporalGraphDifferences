package metrics.unary;

import metrics.Metric;
import org.jgrapht.Graph;
import results.Result;

import java.util.HashMap;

public class TMH implements Metric {
    @Override
    public Result<Long> compute(Graph<Integer, Integer> graph, HashMap<Integer, Integer[]> secondaryIndex, String name) {
        return null;
    }
}
