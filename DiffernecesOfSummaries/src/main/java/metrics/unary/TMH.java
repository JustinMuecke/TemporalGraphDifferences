package metrics.unary;

import datamodel.ExtGraph;
import metrics.UnaryMetric;
import results.Result;
import results.unaryResults.TMHResult;

public class TMH implements UnaryMetric {
    @Override
    public Result compute(ExtGraph graph) {
        System.out.println("[Unary] Calculating TMH");
        long start = System.currentTimeMillis();
        float sum = 0;
        float sumsquared = 0;
        for(Integer v : graph.getGraph().vertexSet()){
            int degree = graph.getGraph().degreeOf(v);
            if(degree < 0){
                System.out.println("TMH: Degree < 0 for " + v);
                continue;
            }
            sum += degree;
            if(sum < 0){
                System.out.println("Possible Overflow");
            }
            sumsquared += degree * degree;
            if(sum < 0){
                System.out.println("Possible Overflow");
            }
        }
        System.out.println("[Unary] TMH Values: " + sum + " ; " + sumsquared);
        long compTime = System.currentTimeMillis() - start;
        return new TMHResult(graph.getName(), sumsquared/sum, compTime);
    }
}
