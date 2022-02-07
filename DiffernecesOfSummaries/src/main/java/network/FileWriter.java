package network;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import results.MetricTypes;

public class FileWriter {

    private static final Logger logger = LogManager.getLogger(FileWriter.class);

    public static void writeToFile(String name, MetricTypes metricType, Float diff){
        //TODO Write result to file
        System.out.println("Metric: " + metricType.toString() + " on " + name  + " = " + diff.toString());
    }
}
