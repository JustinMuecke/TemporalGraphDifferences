package results;

public class AvgSizeOfEQClassResult extends UnaryResult {
    public AvgSizeOfEQClassResult(String database1, Float difference) {
        super(MetricTypes.AVG_SIZE_OF_EQC, database1, difference);
    }
}
