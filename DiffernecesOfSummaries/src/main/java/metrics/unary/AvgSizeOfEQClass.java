package metrics.unary;

import datamodel.ExtGraph;
import metrics.Metric;
import results.AvgSizeOfEQClassResult;
import results.MetricTypes;

public class AvgSizeOfEQClass implements Metric {


    @Override
    public AvgSizeOfEQClassResult compute(ExtGraph graph) {
        int sum = 0;
        for(Integer[] collected : graph.getSecondaryIndex().values()){
            sum += collected.length;
        }
        float avg = (float) sum / graph.getResults().get(MetricTypes.NUMBER_OF_EQC);
        return new AvgSizeOfEQClassResult(graph.getName(), avg);
    }
}
