package metrics;

import datamodel.ExtGraph;
import results.Result;

public interface UnaryMetric {

    Result compute(ExtGraph graph);
}