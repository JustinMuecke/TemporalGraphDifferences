import com.orientechnologies.orient.core.db.ODatabaseSession;

import database.SecondaryIndex;
import network.DBConnectionFailedException;
import network.DBConnector;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.zip.GZIPInputStream;
import database.Imprint;


public class Main {



    private static List<ODatabaseSession> getDatabaseSession(String dbName) throws DBConnectionFailedException{
        return DBConnector.getDatabaseSessions(dbName).orElseThrow(() -> new DBConnectionFailedException("List of Sessions Empty"));
    }

    private static List<Graph> createGraphs(List<ODatabaseSession> sessionList){


        return null;
    }



    public static void main(String[] args)  {

        List<ODatabaseSession> sessionList;
        try {
            sessionList = DBConnector.getDatabaseSessions("justin-test").orElseThrow(() -> new DBConnectionFailedException("List of Sessions Empty"));
            List<Integer> vertices = Queries.getVertices(sessionList.get(0)).orElseThrow(() -> new DBConnectionFailedException("Couldnt get Vertices"));
            System.out.println(vertices.toString());
        } catch (DBConnectionFailedException e) {
            e.printStackTrace();
        }



    }
}
