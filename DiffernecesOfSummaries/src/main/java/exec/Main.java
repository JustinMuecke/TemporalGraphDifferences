package exec;

import com.orientechnologies.orient.core.db.ODatabaseSession;
import datamodel.ExtGraph;
import metrics.BinaryMetric;
import metrics.UnaryMetric;
import metrics.binary.EdgeJaccard;
import metrics.binary.GED;
import metrics.binary.KLD;
import metrics.binary.VertexJaccard;
import metrics.unary.*;
import network.DBConnectionFailedException;
import network.DBConnector;
import network.FileWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    private static String dbName = "SchemEx-2016";
    private static boolean computeUnaryMetrics = true;
    private static boolean computeBinaryMetrics = true;


    private static List<Optional<ODatabaseSession>> getDatabaseSession(String dbName) throws DBConnectionFailedException{
        return DBConnector.getDatabaseSessions(dbName).orElseThrow(() -> new DBConnectionFailedException("List of Sessions Empty"));
    }

    private static UnaryMetric[] createUnaryMetricList(){
        return new UnaryMetric[]{new NumberOfEQClasses(),
                new AvgSizeOfEQClass(),
                new AvgNumberOfEdges(),
                new Comp(),
                new TMH()};
    }

    private static BinaryMetric[] createBinaryMetricList(){
        return new BinaryMetric[]{new EdgeJaccard(), new VertexJaccard(), new GED(), new KLD()};
    }

    public static void main(String[] args)  {

        List<Optional<ODatabaseSession>> sessionList;
        ExtGraph[] graphList = null;
        UnaryMetric[] unaryMetricList = createUnaryMetricList();
        try {
            sessionList = getDatabaseSession(dbName);
            graphList = ExtGraph.createGraphs(sessionList);
        } catch (DBConnectionFailedException e) {
            e.printStackTrace();
        }
        if(graphList == null){
            logger.error("Couldn't load Graphs");
            return;
        }
        // Compute Unary Metrics
        if(computeUnaryMetrics) {
            for (int i = 0; i < graphList.length; i++) {
                System.out.println("[Unary] Calculating Timestep: " + i);

                graphList[i].computeUnaryMetrics(unaryMetricList);
            }

            try {
                FileWriter.initializeCSVFile("Results/" + dbName + "-unaryResults.csv", false, false);
                FileWriter.initializeCSVFile("Results/" + dbName + "-unaryCompTimes.csv", true, false);
                for (ExtGraph g : graphList) {
                    FileWriter.writeResultToFile(g.getUnaryResults(), g.getName(), "Results/" + dbName + "-unaryResults.csv", false, "");
                    FileWriter.writeCompTimeToFile(g.getUnaryCompTimes(), g.getName(), "Results/" + dbName + "-unaryCompTimes.csv", false, "");
                }
            } catch (IOException e) {
                logger.error("Couldnt write to File");
            }
        }
        if(computeBinaryMetrics) {

            //Compute Binary Metrics
            BinaryMetric[] binaryMetrics = createBinaryMetricList();
            if (graphList.length <= 1) {
                return;
            }
            for (int i = 1; i < graphList.length; i++) {
                System.out.println("[Binary] Calculating Timestep: " + i);
                graphList[i].computeBinaryMetrics(binaryMetrics, graphList[i - 1]);
            }
            try {
                FileWriter.initializeCSVFile("Results/" + dbName + "-binaryResults.csv", false, true);
                FileWriter.initializeCSVFile("Results/" + dbName + "-binaryCompTimes.csv", true, true);
                for (int i = 1; i < graphList.length; i++) {
                    FileWriter.writeResultToFile(graphList[i].getBinaryResults(), graphList[i].getName(), "Results/" + dbName + "-binaryResults.csv", true, graphList[i - 1].getName());
                    FileWriter.writeCompTimeToFile(graphList[i].getBinaryCompTimes(), graphList[i].getName(), "Results/" + dbName + "-binaryCompTimes.csv", true, graphList[i - 1].getName());
                }
            } catch (IOException e) {
                logger.error("Couldnt write to fiel");
            }
        }

    }
}
