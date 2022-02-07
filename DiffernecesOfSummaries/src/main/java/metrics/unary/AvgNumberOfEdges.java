package metrics.unary;

import datamodel.ExtGraph;
import metrics.Metric;
import results.AvgNumberOfEdgesResult;
import results.MetricTypes;
import results.Result;

public class AvgNumberOfEdges implements Metric {
    @Override
    public Result compute(ExtGraph graph) {
        int numberOfEdges = graph.getGraph().edgeSet().size();
        float avg = numberOfEdges / graph.getResults().get(MetricTypes.NUMBER_OF_EQC);
        return new AvgNumberOfEdgesResult(graph.getName(), avg);
    }
}
