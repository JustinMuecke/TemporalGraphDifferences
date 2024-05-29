package results.unaryResults;

import results.MetricTypes;
import results.UnaryResult;

public class AvgDegreeResult extends UnaryResult {
    public AvgDegreeResult(String database, float difference, long compTime) {
        super(MetricTypes.AVG_DEG, database, difference, compTime);
    }
}
