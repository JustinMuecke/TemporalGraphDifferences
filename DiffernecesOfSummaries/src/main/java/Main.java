import com.orientechnologies.orient.core.db.ODatabaseSession;
import datamodel.ExtGraph;
import metrics.Metric;
import metrics.unary.*;
import network.DBConnectionFailedException;
import network.DBConnector;
import network.FileWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;


public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    private static List<ODatabaseSession> getDatabaseSession(String dbName) throws DBConnectionFailedException{
        return DBConnector.getDatabaseSessions(dbName).orElseThrow(() -> new DBConnectionFailedException("List of Sessions Empty"));
    }

    private static Metric[] createUnaryMetricList(){
        return new Metric[]{new NumberOfEQClasses(), new AvgSizeOfEQClass(), new AvgNumberOfEdges(), new Comp(), new TMH()};
    }

    public static void main(String[] args)  {
        List<ODatabaseSession> sessionList;
        ExtGraph[] graphList = null;
        Metric[] unaryMetricList = createUnaryMetricList();
        try {
            sessionList = getDatabaseSession("justin-test");
            graphList = ExtGraph.createGraphs(sessionList);
        } catch (DBConnectionFailedException e) {
            e.printStackTrace();
        }
        if(graphList == null){
            logger.error("Couldn't load Graphs");
            return;
        }
        for (ExtGraph extGraph : graphList) {
            extGraph.computeUnaryMetrics(unaryMetricList);
        }
        try {
            FileWriter.initializeUnaryCSVFile("Results/unaryResults.csv", false);
            FileWriter.initializeUnaryCSVFile("Results/unaryCompTimes.csv", true);
            for (ExtGraph g : graphList) {
                FileWriter.writeToUnaryFile(g.getResults(), g.getName());
                FileWriter.writeToUnaryCompTimeFile(g.getCompTimes(), g.getName());

            }
        } catch( IOException e){
            logger.error("Couldnt write to File");
        }

    }
}
