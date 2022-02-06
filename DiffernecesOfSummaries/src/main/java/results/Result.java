package results;

public abstract class Result {

    private final MetricTypes metric;
    private final float difference;

    public Result(MetricTypes metric, float difference) {
        this.metric = metric;
        this.difference = difference;
    }

    @Override
    public String toString() {
        return "metrics.Metric "+ metric + ":\n" +
                "Difference:" + difference + "\n";
    }
}
