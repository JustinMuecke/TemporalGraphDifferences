package results.unaryResults;

import results.MetricTypes;
import results.UnaryResult;

public class TMHResult extends UnaryResult {
    public TMHResult(String database, float difference, long compTime) {
        super(MetricTypes.TMH, database, difference, compTime);
    }
}
