package metrics.unary;

import datamodel.ExtGraph;
import metrics.Metric;
import results.NumberOfEQCResult;
import results.Result;

public class NumberOfEQClasses implements Metric{

    @Override
    public Result compute(ExtGraph graph) {
        return new NumberOfEQCResult(graph.getName(), graph.getSecondaryIndex().keySet().size());
    }
}
