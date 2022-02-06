package results;

public abstract class Result<T> {

    private final MetricTypes metric;
    private final String database1;
    private final String database2;
    private final T difference;

    public Result(MetricTypes metric, String database1, String database2, T difference) {
        this.metric = metric;
        this.database1 = database1;
        this.database2 = database2;
        this.difference = difference;
    }

    @Override
    public String toString() {
        return "metrics.Metric "+ metric + ":\n" +
                "First database: " + database1 + ", " +
                "Second database: " + database2 + ", " +
                "Difference:" + difference + "\n";
    }
}
