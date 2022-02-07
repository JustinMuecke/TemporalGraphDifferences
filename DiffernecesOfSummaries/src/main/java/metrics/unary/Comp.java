package metrics.unary;

import datamodel.Edge;
import datamodel.ExtGraph;
import metrics.Metric;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import results.CompResult;
import results.Result;

import java.util.List;
import java.util.Set;

public class Comp implements Metric {
    @Override
    public Result compute(ExtGraph graph) {
        ConnectivityInspector<Integer, Edge> connectivityInspector = new ConnectivityInspector<>(graph.getGraph());
        List<Set<Integer>> connectedSets = connectivityInspector.connectedSets();
        return new CompResult(graph.getName(), connectedSets.size());
    }
}
