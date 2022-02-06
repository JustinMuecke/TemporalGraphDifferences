package metrics;

import org.jgrapht.Graph;
import results.Result;

import java.util.HashMap;

public interface Metric {

    Result<Long> compute(Graph<Integer,Integer> graph, HashMap<Integer, Integer[]> secondaryIndex, String name);

}
