package metrics.unary;

import datamodel.ExtGraph;
import metrics.UnaryMetric;
import results.Result;
import results.unaryResults.TMHResult;

import java.util.LinkedList;
import java.util.List;

public class TMH implements UnaryMetric {
    @Override
    public Result compute(ExtGraph graph) {
        System.out.println("[Unary] Calculating " + this.getClass());
        try {
            List<Float> squaredsum = new LinkedList<>();
            long start = System.currentTimeMillis();
            float sum = 0;
            for (Integer v : graph.getGraph().vertexSet()) {
                float degree = graph.getGraph().degreeOf(v);
                if (degree < 0) {
                    System.out.println("TMH: Degree < 0 for " + v);
                    continue;
                }
                sum += degree;
                if (degree * degree < 0) {
                    System.out.println("Possible Overflow");
                }
                squaredsum.add(degree * degree);
            }
            float result = 0;
            for (Float s : squaredsum) {
                result += s / sum;
            }
            long compTime = System.currentTimeMillis() - start;
            return new TMHResult(graph.getName(), result, compTime);
        } catch (Exception e){
            System.out.println("[Unary] Couldn't Calculate " + this.getClass());
            return null;
        }
    }
}
