package results;

public class AvgLinksResult extends Result{

    public AvgLinksResult(String database1, String database2, float difference) {
        super(MetricTypes.AVG_LINK_PER_NODE, database1, database2, difference);
    }
}
