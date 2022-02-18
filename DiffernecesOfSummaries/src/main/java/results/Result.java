package results;

public abstract class Result {

    private final MetricTypes metric;
    private final float difference;
    private final long compTime;

    public Result(MetricTypes metric, float difference, long compTime) {
        this.metric = metric;
        this.difference = difference;
        this.compTime = compTime;
    }

    @Override
    public String toString() {
        return "metrics.Metric "+ metric + ":\n" +
                "Difference:" + difference + "\n";
    }

    public MetricTypes getMetric() {
        return metric;
    }

    public float getDifference() {
        return difference;
    }

    public long getCompTime() {
        return compTime;
    }
}
