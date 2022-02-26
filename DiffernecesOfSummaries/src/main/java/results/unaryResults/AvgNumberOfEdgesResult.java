package results.unaryResults;

import results.MetricTypes;
import results.UnaryResult;

public class AvgNumberOfEdgesResult extends UnaryResult {
    public AvgNumberOfEdgesResult(String database, float difference, long compTime) {
        super(MetricTypes.AVG_NUMBERS_OF_EDGES, database, difference, compTime);
    }
}
