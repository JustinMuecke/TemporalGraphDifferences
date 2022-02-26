package metrics.unary;

import datamodel.ExtGraph;
import metrics.UnaryMetric;
import results.unaryResults.AvgSizeOfEQClassResult;
import results.MetricTypes;

public class AvgSizeOfEQClass implements UnaryMetric {


    @Override
    public AvgSizeOfEQClassResult compute(ExtGraph graph) {
        long start = System.currentTimeMillis();
        int sum = 0;
        for(Integer[] collected : graph.getSecondaryIndex().values()){
            sum += collected.length;
        }
        long compTime = System.currentTimeMillis() - start;
        float avg = (float) sum / graph.getUnaryResults().get(MetricTypes.NUMBER_OF_EQC);
        return new AvgSizeOfEQClassResult(graph.getName(), avg, compTime);
    }
}
