package results;

public abstract class UnaryResult extends Result{

    private final String database;

    public UnaryResult(MetricTypes metric, String database, float difference, long compTime) {
        super(metric, difference, compTime);
        this.database = database;
    }

    public String getDatabase() {
        return database;
    }
}
