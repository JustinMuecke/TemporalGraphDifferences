package database;

// import instumentation.InstrumentationAgent;
//import org.apache.log4j.LogManager;
//import org.apache.log4j.Logger;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class SecondaryIndex implements Serializable {
    //private static final Logger logger = LogManager.getLogger(SecondaryIndex.class.getSimpleName());

    private static final boolean TRACK_PAYLOAD_DETAILS = true;
    private final static Gson gson = new Gson();

    public SecondaryIndex(boolean trackAllChanges, boolean trackMandatory, boolean trackExecutionTimes, String indexFile) {
        storedImprints = new HashMap<>();
        this.indexFile = "IndexFiles/"+indexFile;
        this.trackAllChanges = trackAllChanges;
        this.trackMandatory = trackMandatory;
        this.trackExecutionTimes = trackExecutionTimes;
    }

    /*
        Sync objects for parallel access
     */
    private transient Object readSyncSchemaLinks = new Object();
    private transient Object writeSyncSchemaLinks = new Object();

    private transient Object readSyncImprint = new Object();
    private transient Object writeSyncImprint = new Object();


    /*
    general settings
     */
    private final String indexFile;
    private final boolean trackAllChanges;
    private final boolean trackMandatory;
    private final boolean trackExecutionTimes;

    //schema elements and their summarized instance (IDs)
    private HashMap<Integer, Set<Integer>> schemaElementToImprint;

    //imprints stored by ID, imprints also hold links to schema elements
    private HashMap<Integer, Imprint> storedImprints;

    public HashMap<Integer, Imprint> getStoredImprints(){
        return storedImprints;
    }

    private HashMap<Integer, Set<Integer>> schemaRelationToEdgeImprints;



    //if schema elements should be removed first collect ids here to avoid unnecessary updates (
    private transient HashSet<Integer> schemaElementsToBeRemoved = new HashSet<>();

    public HashSet<Integer> getSchemaElementsToBeRemoved() {
        return schemaElementsToBeRemoved;
    }

    public void addPayload(int imprintID, Set<String> payload){
        //TODO: count payload changes
        synchronized (readSyncImprint){
            synchronized (writeSyncImprint){
                storedImprints.get(imprintID)._payload.addAll(payload);
            }
        }
    }

    public void removePayload(int imprintID, Set<String> payload){
        //TODO: count payload changes
        synchronized (readSyncImprint){
            synchronized (writeSyncImprint){
                storedImprints.get(imprintID)._payload.removeAll(payload);
            }
        }
    }

    public static SecondaryIndex readFromJson(String filePath){
        try{
            FileInputStream fis = new FileInputStream(filePath);
            DataInputStream dis = new DataInputStream(fis);
            String jsonString = dis.readUTF();
            SecondaryIndex secIndex = gson.fromJson(jsonString, SecondaryIndex.class);
            dis.close();
            fis.close();
        return secIndex;
        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println("IOException");
            return null;
        }
        catch(JsonSyntaxException e){
            System.out.println("JSON Syntax Exception");
            return null;
        }

    }

    @Override
    public String toString() {
        return "SecondaryIndex{" +
                "trackAllChanges=" + trackAllChanges +
                ", trackMandatory=" + trackMandatory +
                ", trackExecutionTimes=" + trackExecutionTimes +
                ", storedImprints=" + storedImprints +
                '}';
    }

    public String toJson(){
        StringBuilder sb = new StringBuilder();
        sb.append("{\"SecondaryIndex\" : {\n");
        sb.append("\"storedImprints\" : {\n");
        storedImprints.forEach((k, v) -> sb.append("\"" + k.toString() +"\" : " + v.toJson()+ ","));
        sb.deleteCharAt(sb.toString().length()-1);
        sb.append("\n},\n");
        sb.append("\"indexFile\" : \"" + indexFile + "\",\n");
        sb.append("\" trackAllChanges \": " + String.valueOf(trackAllChanges)+ ",\n");
        sb.append("\" trackMandatory \": " + String.valueOf(trackMandatory)+ ",\n");
        sb.append("\" trackExecutionTime \": " + String.valueOf(trackExecutionTimes)+ "\n");
        sb.append("}\n}");
        return sb.toString();
    }
}
    