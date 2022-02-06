package results;

public abstract class UnaryResult<T> extends Result{

    private String database;

    public UnaryResult(MetricTypes metric, String database, float difference) {
        super(metric, difference);
        this.database = database;
    }

    public String getDatabase() {
        return database;
    }
}
