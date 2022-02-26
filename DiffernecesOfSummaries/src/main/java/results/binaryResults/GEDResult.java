package results.binaryResults;

import results.BinaryResult;
import results.MetricTypes;

public class GEDResult extends BinaryResult {
    public GEDResult(float difference, long compTime, String database1, String database2) {
        super(MetricTypes.GED, difference, compTime, database1, database2);
    }
}
