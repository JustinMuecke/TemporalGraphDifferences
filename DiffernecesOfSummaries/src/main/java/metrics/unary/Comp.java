package metrics.unary;

import datamodel.Edge;
import datamodel.ExtGraph;
import metrics.UnaryMetric;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import results.unaryResults.CompResult;
import results.Result;

import java.util.List;
import java.util.Set;

public class Comp implements UnaryMetric {
    @Override
    public Result compute(ExtGraph graph) {
        System.out.println("[Unary] Calculating " + this.getClass());
        try {

            long start = System.currentTimeMillis();
            ConnectivityInspector<Integer, Edge> connectivityInspector = new ConnectivityInspector<>(graph.getGraph());
            List<Set<Integer>> connectedSets = connectivityInspector.connectedSets();
            long compTime = System.currentTimeMillis() - start;
            return new CompResult(graph.getName(), connectedSets.size(), compTime);
        } catch (Exception e){
        System.out.println("[Unary] Couldn't Calculate " + this.getClass());
        return null;
    }
    }
}
