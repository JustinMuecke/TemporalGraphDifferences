package results;

public abstract class UnaryResult extends Result{

    private final String database;

    public UnaryResult(MetricTypes metric, String database, float difference) {
        super(metric, difference);
        this.database = database;
    }

    public String getDatabase() {
        return database;
    }
}
