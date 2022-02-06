package results;

public class NumberOfEdgesResult extends MetricResult{
    public NumberOfEdgesResult(String database1, String database2, int difference) {
        super(MetricTypes.NUMBER_OF_EDGES, database1, database2, difference);
    }
}
