package results;

public class CompResult extends UnaryResult{
    public CompResult(String database, float difference) {
        super(MetricTypes.COMP, database, difference);
    }
}
