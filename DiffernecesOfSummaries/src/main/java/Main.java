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


    public static Optional<SecondaryIndex> readGZIPPEDfile() throws IOException{
        try{
            SecondaryIndex index = SecondaryIndex.readFromJson("/home/justinmucke/git/TemporalGraphDifferences/DiffernecesOfSummaries/Indicies/secondaryIndex-0.json");
           return Optional.of(index);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("FAILED TO READ INDEX");
            return Optional.empty();
        }
        
    }


    public static void main(String[] args)  {
        try{
            SecondaryIndex index = readGZIPPEDfile().orElseThrow(() -> new IOException("Failed Secondary Index read"));
            System.out.println(index.toString());


        } catch(IOException e){
            e.printStackTrace();
            System.out.println("FAILED TO READ INDEX");

        }
        /*
        List<ODatabaseSession> sessionList;
        try {
            sessionList = DBConnector.getDatabaseSessions("justin-imp-test").orElseThrow(() -> new DBConnectionFailedException("List of Sessions Empty"));
            System.out.println(Metrics.changeInAverageLinks(sessionList.get(0), sessionList.get(1)));
            System.out.println(Metrics.changeInNumberOfNOdes(sessionList.get(0), sessionList.get(1)));
            System.out.println(Metrics.changeInNumberOfEdges(sessionList.get(0), sessionList.get(1)));
        } catch (DBConnectionFailedException e) {
            e.printStackTrace();
        }
        */


    }
}
