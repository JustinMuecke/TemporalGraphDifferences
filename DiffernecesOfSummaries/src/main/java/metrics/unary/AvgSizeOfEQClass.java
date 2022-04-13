package metrics.unary;

import datamodel.ExtGraph;
import metrics.UnaryMetric;
import results.unaryResults.AvgSizeOfEQClassResult;
import results.MetricTypes;

public class AvgSizeOfEQClass implements UnaryMetric {


    @Override
    public AvgSizeOfEQClassResult compute(ExtGraph graph) {
        System.out.println("[Unary] Calculating " + this.getClass());

        try{

        long start = System.currentTimeMillis();
        int sum = 0;
        for(Integer collected : graph.getSecondaryIndex().values()){
            sum += collected;
        }
        long compTime = System.currentTimeMillis() - start;
        float avg = (float) sum / graph.getUnaryResults().get(MetricTypes.NUMBER_OF_EQC);
        return new AvgSizeOfEQClassResult(graph.getName(), avg, compTime);
        } catch (Exception e){
            System.out.println("[Unary] Couldn't Calculate " + this.getClass());
            return null;
        }
    }
}
