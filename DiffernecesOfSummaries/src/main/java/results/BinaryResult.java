package results;

public abstract class BinaryResult extends Result{


    private final String database1;
    private final String database2;

    public BinaryResult(MetricTypes metric, float difference, long compTime, String database1, String database2) {
        super(metric, difference, compTime);
        this.database1 = database1;
        this.database2 = database2;
    }

    public String getDatabase1() {
        return database1;
    }

    public String getDatabase2() {
        return database2;
    }
}
