package results.binaryResults;

import results.BinaryResult;
import results.MetricTypes;

public class JaccardVertexResult extends BinaryResult {
    public JaccardVertexResult(float difference, long compTime, String database1, String database2) {
        super(MetricTypes.JACCARD_VERTEX, difference, compTime, database1, database2);
    }
}
