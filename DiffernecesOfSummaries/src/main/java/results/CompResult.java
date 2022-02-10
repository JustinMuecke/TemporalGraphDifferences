package results;

public class CompResult extends UnaryResult{
    public CompResult(String database, float difference, long compTime) {

        super(MetricTypes.COMP, database, difference, compTime);
    }
}
