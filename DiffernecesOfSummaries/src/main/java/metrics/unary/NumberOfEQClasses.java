package metrics.unary;

import datamodel.ExtGraph;
import metrics.UnaryMetric;
import results.unaryResults.NumberOfEQCResult;
import results.Result;

public class NumberOfEQClasses implements UnaryMetric {

    @Override
    public Result compute(ExtGraph graph) {
        long start = System.currentTimeMillis();
        float size = graph.getSecondaryIndex().keySet().size();
        long compTime = System.currentTimeMillis() - start;
        return new NumberOfEQCResult(graph.getName(), size, compTime);
    }
}
