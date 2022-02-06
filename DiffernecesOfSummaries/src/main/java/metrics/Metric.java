package metrics;

import com.orientechnologies.orient.core.db.ODatabaseSession;
import results.AvgLinksResult;
import results.MetricTypes;
import results.Result;

public interface Metric {

    Graph graph;
    Result result = null;

    long calculateMetric(ODatabaseSession database);

}
