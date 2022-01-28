package database;
import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class SecondaryIndex implements Serializable {

    private static final boolean TRACK_PAYLOAD_DETAILS = true;

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
    transient HashSet<Integer> schemaElementsToBeRemoved = new HashSet<>();
   

    private SecondaryIndex(boolean trackAllChanges, boolean trackMandatory, boolean trackExecutionTimes, String indexFile) {
        schemaElementToImprint = new HashMap<>();
        storedImprints = new HashMap<>();
        this.indexFile = "IndexFiles/"+indexFile;
        this.trackAllChanges = trackAllChanges;
        this.trackMandatory = trackMandatory;
        this.trackExecutionTimes = trackExecutionTimes;
    }

//    private static SecondaryIndex singletonInstance = null;
//
//    public static SecondaryIndex getInstance() {
//        return singletonInstance;
//    }

//    public static void deactivate() {
//        singletonInstance = null;
//    }

//    public static void init(boolean trackAllChanges, boolean trackMandatory, boolean trackExecutionTimes, String indexFile, boolean loadPreviousIndex) throws IOException {
//        if (!loadPreviousIndex)
//            singletonInstance = new SecondaryIndex(trackAllChanges, trackMandatory, trackExecutionTimes, indexFile);
//        else {
//            // Reading the object from a file
//            GZIPInputStream gis = new GZIPInputStream(new FileInputStream(indexFile));
//            ObjectInputStream in = new ObjectInputStream(gis);
//            // Method for deserialization of object
//            try {
//                singletonInstance = (SecondaryIndex) in.readObject();
//                singletonInstance.readSyncSchemaLinks = new Object();
//                singletonInstance.writeSyncSchemaLinks = new Object();
//                singletonInstance.readSyncImprint = new Object();
//                singletonInstance.writeSyncImprint = new Object();
//                singletonInstance.schemaElementsToBeRemoved = new HashSet<>();
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//            in.close();
//            gis.close();
//        }
//    }

    public static SecondaryIndex instantiate(boolean trackAllChanges, boolean trackMandatory, boolean trackExecutionTimes, String indexFile, boolean loadPreviousIndex) throws IOException {
        SecondaryIndex secondaryIndex = null;
        if (!loadPreviousIndex)
            secondaryIndex = new SecondaryIndex(trackAllChanges, trackMandatory, trackExecutionTimes, indexFile);
        else {
            // Reading the object from a file
            GZIPInputStream gis = new GZIPInputStream(new FileInputStream(indexFile));
            ObjectInputStream in = new ObjectInputStream(gis);
            // Method for deserialization of object
            try {
                secondaryIndex = (SecondaryIndex) in.readObject();
                secondaryIndex.readSyncSchemaLinks = new Object();
                secondaryIndex.writeSyncSchemaLinks = new Object();
                secondaryIndex.readSyncImprint = new Object();
                secondaryIndex.writeSyncImprint = new Object();
                secondaryIndex.schemaElementsToBeRemoved = new HashSet<>();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            in.close();
            gis.close();
        }
        return secondaryIndex;
    }

    public long persist() throws IOException {
        //Saving of object in a file
        GZIPOutputStream gis = new GZIPOutputStream(new FileOutputStream(indexFile));
        ObjectOutputStream out = new ObjectOutputStream(gis);
        // Method for serialization of object
        out.writeObject(this);
        out.close();
        gis.close();
        return new File(indexFile).length();
    }


    /*
        Sync objects for parallel access
     */
    transient Object readSyncSchemaLinks = new Object();
    transient Object writeSyncSchemaLinks = new Object();

    transient Object readSyncImprint = new Object();
    transient Object writeSyncImprint = new Object();




    public HashSet<Integer> getSchemaElementsToBeRemoved() {
        return schemaElementsToBeRemoved;
    }

}