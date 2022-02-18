package metrics.unary;

import datamodel.ExtGraph;
import metrics.Metric;
import results.AvgNumberOfEdgesResult;
import results.MetricTypes;
import results.Result;

public class AvgNumberOfEdges implements Metric {
    @Override
    public Result compute(ExtGraph graph) {
        long startTime = System.currentTimeMillis();
        int numberOfEdges = graph.getGraph().edgeSet().size();
        float avg = numberOfEdges / graph.getResults().get(MetricTypes.NUMBER_OF_EQC);
        long compTime = System.currentTimeMillis() - startTime;
        return new AvgNumberOfEdgesResult(graph.getName(), avg, compTime);

    }
}
