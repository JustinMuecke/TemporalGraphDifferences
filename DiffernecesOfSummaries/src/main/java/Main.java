import com.google.gson.Gson;

import com.orientechnologies.orient.core.db.ODatabaseSession;
import metrics.Metrics;
import network.DBConnectionFailedException;
import network.DBConnector;

import java.util.List;


public class Main {

    private static Gson gson = new Gson();


    public static void main(String[] args)  {

        List<ODatabaseSession> sessionList;
        try {
            sessionList = DBConnector.getDatabaseSessions("justin-test").orElseThrow(() -> new DBConnectionFailedException("List of Sessions Empty"));
            System.out.println(Metrics.changeInAverageLinks(sessionList.get(0), sessionList.get(1)));
            System.out.println(Metrics.changeInNumberOfNOdes(sessionList.get(0), sessionList.get(1)));
            System.out.println(Metrics.changeInNumberOfEdges(sessionList.get(0), sessionList.get(1)));
        } catch (DBConnectionFailedException e) {
            e.printStackTrace();
        }



    }
}
