package metrics.unary;

import datamodel.ExtGraph;
import metrics.UnaryMetric;
import results.unaryResults.AvgNumberOfEdgesResult;
import results.MetricTypes;
import results.Result;

public class AvgNumberOfEdges implements UnaryMetric {
    @Override
    public Result compute(ExtGraph graph) {
        System.out.println("[Unary] Calculating " + this.getClass());
        try{
        long startTime = System.currentTimeMillis();
        int numberOfEdges = graph.getGraph().edgeSet().size();
        float avg = numberOfEdges / graph.getUnaryResults().get(MetricTypes.NUMBER_OF_EQC);
        long compTime = System.currentTimeMillis() - startTime;
        return new AvgNumberOfEdgesResult(graph.getName(), avg, compTime);
        } catch (Exception e){
            System.out.println("[Unary] Couldn't Calculate " + this.getClass());
            return null;
        }
    }
}
