package metrics;

import datamodel.ExtGraph;
import results.Result;

public interface BinaryMetric {

    Result compute(ExtGraph graph1, ExtGraph graph2);
}
