package metrics.unary;

import datamodel.ExtGraph;
import metrics.Metric;
import results.Result;
import results.TMHResult;

public class TMH implements Metric {
    @Override
    public Result compute(ExtGraph graph) {
        long start = System.currentTimeMillis();
        float sum = 0;
        float sumsquared = 0;
        for(Integer v : graph.getGraph().vertexSet()){
            int degree = graph.getGraph().degreeOf(v);
            sum += degree;
            sumsquared += degree * degree;
        }
        long compTime = System.currentTimeMillis() - start;
        return new TMHResult(graph.getName(), sumsquared/sum, compTime);
    }
}
