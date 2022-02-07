package metrics;

import datamodel.ExtGraph;
import results.Result;

public interface Metric {

    Result compute(ExtGraph graph);

}