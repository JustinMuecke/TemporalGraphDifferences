package results;

public class NumberOfNodesResult extends Result{
    public NumberOfNodesResult(String database1, String database2, int difference) {
        super(MetricTypes.NUMBER_OF_NODES, database1, database2, difference);
    }
}
