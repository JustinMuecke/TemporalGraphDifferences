package results.binaryResults;

import results.BinaryResult;
import results.MetricTypes;

public class JaccardEdgeResult extends BinaryResult {
    public JaccardEdgeResult(float difference, long compTime, String database1, String database2) {
        super(MetricTypes.JARCCARD_EDGE, difference, compTime, database1, database2);
    }
}
