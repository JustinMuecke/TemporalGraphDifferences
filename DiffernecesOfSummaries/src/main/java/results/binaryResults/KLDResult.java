package results.binaryResults;

import results.BinaryResult;
import results.MetricTypes;

public class KLDResult extends BinaryResult {
    public KLDResult(float difference, long compTime, String database1, String database2) {
        super(MetricTypes.KLD, difference, compTime, database1, database2);
    }
}
