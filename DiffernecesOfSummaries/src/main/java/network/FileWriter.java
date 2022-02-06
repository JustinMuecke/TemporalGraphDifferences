package network;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import results.Result;

public class FileWriter {

    private static final Logger logger = LogManager.getLogger(FileWriter.class);

    public static void writeToFile(Result<Long> result){
        //TODO Write result to file
        logger.info(result.toString());
    }
}
