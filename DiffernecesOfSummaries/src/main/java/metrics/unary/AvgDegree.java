package metrics.unary;

import datamodel.ExtGraph;
import metrics.UnaryMetric;
import results.Result;
import results.unaryResults.AvgDegreeResult;

public class AvgDegree implements UnaryMetric {
    @Override
    public Result compute(ExtGraph graph) {
        float value =  graph.getSecondaryIndex().keySet()
                .stream()
                .map(v -> graph.getGraph().inDegreeOf(v) + graph.getGraph().outDegreeOf(v))
                .reduce(0, Integer::sum);
        float avg = value / graph.getSecondaryIndex().keySet().size();
        return new AvgDegreeResult(graph.getName(), value, 0);
    }
}
