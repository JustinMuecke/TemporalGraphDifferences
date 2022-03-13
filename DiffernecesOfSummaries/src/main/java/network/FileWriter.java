package network;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import results.MetricTypes;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;

public class FileWriter {

    private static final Logger logger = LogManager.getLogger(FileWriter.class);

    public static void writeResultToFile(HashMap<MetricTypes, Float> results, String name, String filepath, boolean binary, String refname) throws IOException {
        String csv = "";
        if(binary){
            csv = createBinaryCSVString(results, name, refname);
        }
        else {
            csv = createUnaryCSVString(results, name);
        }
        if(csv == ""){
            logger.error("Couldnt read results");
            return;
        }

        RandomAccessFile stream = new RandomAccessFile(filepath, "rw");
        stream.seek(stream.length());
        getFileChannel(csv, stream);
    }

    public static void writeCompTimeToFile(HashMap<MetricTypes, Long> compTimes, String name, String filepath, boolean binary, String refname) throws IOException{
        String csv = "";
        if(binary){
            csv = createBinaryCompTimeString(compTimes, name, refname);
        }
        else{ csv = createUnaryCompTimeString(compTimes, name);}
        if(csv.equals("")){
            logger.error("Couldnt read results");
            return;
        }

        RandomAccessFile stream = new RandomAccessFile(filepath, "rw");
        stream.seek(stream.length());
        getFileChannel(csv, stream);
    }


    public static void initializeCSVFile(String filepath, boolean addGraphCreation, boolean binary) throws IOException {
        String header = "Name";
        if(binary) {
            header += ", Name 2nd";
        }
        if(addGraphCreation){
            header += ", Graph Creation";
        }
        if(binary){
            header += ", JaccardVertex, JaccardEdge, GED, KLD\n";
        }
        if(!binary) {
            header += ", Number of EQC, Average Size of EQC, Average Number of Edges, TMH, Comp\n";
        }
        RandomAccessFile stream = new RandomAccessFile(filepath, "rw");
        getFileChannel(header, stream);
    }

    private static void getFileChannel(String header, RandomAccessFile stream) throws IOException {
        FileChannel channel = stream.getChannel();

        byte[] strBytes = header.getBytes();
        ByteBuffer buffer = ByteBuffer.allocate(strBytes.length);
        buffer.put(strBytes);
        buffer.flip();
        channel.write(buffer);
        stream.close();
        channel.close();
    }


    private static String createUnaryCSVString(HashMap<MetricTypes, Float> results, String name){
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(",")
                .append(results.get(MetricTypes.NUMBER_OF_EQC))
                .append(",")
                .append(results.get(MetricTypes.AVG_SIZE_OF_EQC))
                .append(",")
                .append(results.get(MetricTypes.AVG_NUMBERS_OF_EDGES))
                .append(",")
                .append(results.get(MetricTypes.TMH))
                .append(",")
                .append(results.get(MetricTypes.COMP))
                .append("\n");
        String csv = sb.toString();
        csv = csv.replace("Null", "0");
        return csv;
    }

    private static String createUnaryCompTimeString(HashMap<MetricTypes, Long> compTimes, String name){
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(",")
                .append(compTimes.get(MetricTypes.GRPAH_CREATION))
                .append(",")
                .append(compTimes.get(MetricTypes.NUMBER_OF_EQC))
                .append(",")
                .append(compTimes.get(MetricTypes.AVG_SIZE_OF_EQC))
                .append(",")
                .append(compTimes.get(MetricTypes.AVG_NUMBERS_OF_EDGES))
                .append(",")
                .append(compTimes.get(MetricTypes.TMH))
                .append(",")
                .append(compTimes.get(MetricTypes.COMP))
                .append("\n");
        String csv = sb.toString();
        csv = csv.replace("Null", "0");
        return csv;
    }

    private static String createBinaryCSVString(HashMap<MetricTypes, Float> results, String name, String refname){
        StringBuilder sb = new StringBuilder();
        sb.append(refname).append(",")
                .append(name).append(",")
                .append(results.get(MetricTypes.JACCARD_VERTEX)).append(",")
                .append(results.get(MetricTypes.JARCCARD_EDGE)).append(",")
                .append(results.get(MetricTypes.GED)).append(",")
                .append(results.get(MetricTypes.KLD))
                .append("\n");
        String csv = sb.toString();
        csv = csv.replace("Null", "0");
        return csv;
    }

    private static String createBinaryCompTimeString(HashMap<MetricTypes, Long> compTimes, String name, String refname){
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(",")
                .append(refname).append(",")
                .append(compTimes.get(MetricTypes.JACCARD_VERTEX)).append(",")
                .append(compTimes.get(MetricTypes.JARCCARD_EDGE)).append(",")
                .append(compTimes.get(MetricTypes.GED)).append(",")
                .append(compTimes.get(MetricTypes.KLD))
                .append("\n");
        String csv = sb.toString();
        csv = csv.replace("Null", "0");
        return csv;
    }
}
