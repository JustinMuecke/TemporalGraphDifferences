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

    public static void writeToUnaryFile(HashMap<MetricTypes, Float> results, String name) throws IOException {
        String csv = createUnaryCSVString(results, name);
        logger.info(csv);
        RandomAccessFile stream = new RandomAccessFile("Results/unaryResults.csv", "rw");
        stream.seek(stream.length());
        getFileChannel(csv, stream);
    }

    public static void writeToUnaryCompTimeFile(HashMap<MetricTypes, Long> compTimes, String name) throws IOException{
        String csv = createUnaryCompTimeString(compTimes, name);
        logger.info(csv);
        RandomAccessFile stream = new RandomAccessFile("Results/unaryCompTimes.csv", "rw");
        stream.seek(stream.length());
        getFileChannel(csv, stream);
    }

    public static void initializeUnaryCSVFile(String filepath, boolean addGraphCreation) throws IOException {
        String header = "Name";
        if(addGraphCreation){
            header += ", Graph Creation";
        }
        header += ", Number of EQC, Average Size of EQC, Average Number of Edges, TMH, Comp\n";
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



    public void initializeBinaryCSVFile(){

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
    };
}
