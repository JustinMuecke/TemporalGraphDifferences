package metrics.unary;

import datamodel.ExtGraph;
import metrics.UnaryMetric;
import results.unaryResults.AvgNumberOfEdgesResult;
import results.MetricTypes;
import results.Result;

public class AvgNumberOfEdges implements UnaryMetric {
    @Override
    public Result compute(ExtGraph graph) {
        long startTime = System.currentTimeMillis();
        int numberOfEdges = graph.getGraph().edgeSet().size();
        System.out.println("EDGE SET IN AVG EDGE: " + graph.getGraph().edgeSet().size());
        float avg = numberOfEdges / graph.getUnaryResults().get(MetricTypes.NUMBER_OF_EQC);
        long compTime = System.currentTimeMillis() - startTime;
        return new AvgNumberOfEdgesResult(graph.getName(), avg, compTime);

    }
}
