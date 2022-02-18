package results;

public class AvgNumberOfEdgesResult extends UnaryResult{
    public AvgNumberOfEdgesResult(String database, float difference, long compTime) {
        super(MetricTypes.AVG_NUMBERS_OF_EDGES, database, difference, compTime);
    }
}
