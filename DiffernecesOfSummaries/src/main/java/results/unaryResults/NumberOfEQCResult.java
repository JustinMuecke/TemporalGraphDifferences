package results.unaryResults;

import results.MetricTypes;
import results.UnaryResult;

public class NumberOfEQCResult extends UnaryResult {
    public NumberOfEQCResult(String database, float difference, long compTime) {
        super(MetricTypes.NUMBER_OF_EQC, database, difference, compTime);
    }
}
