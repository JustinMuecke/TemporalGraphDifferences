package datamodel;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;



public class SecondaryIndex implements Serializable {
    //private static final Logger logger = LogManager.getLogger(SecondaryIndex.class.getSimpleName());

    private static final boolean TRACK_PAYLOAD_DETAILS = true;
    private final static Gson gson = new Gson();

    private final HashMap<Integer, Integer[]> schemaElementToImprint;

    public SecondaryIndex(HashMap<Integer, Integer[]> schemaElementToImprint){
        this.schemaElementToImprint = schemaElementToImprint;
    }

    public static SecondaryIndex readFromJson(String filePath){
        try{
            File f = new File(filePath);
            byte[] bytes = Files.readAllBytes(f.toPath());
            String jsonString= new String(bytes, StandardCharsets.UTF_8);
            System.out.println(jsonString);
            return gson.fromJson(jsonString, SecondaryIndex.class);
        }
        catch(IOException e){
            System.out.println("IOException");

            e.printStackTrace();
            return null;
        }
        catch(JsonSyntaxException e){
            System.out.println("JSON Syntax Exception");
            e.printStackTrace();
            return null;
        }

    }

    public HashMap<Integer, Integer[]> getSchemaElementToImprint() {
        return schemaElementToImprint;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

                for(Integer k : schemaElementToImprint.keySet()){
                    sb.append("Key = ").append(k).append(": [");
                    for(Integer v : schemaElementToImprint.get(k)){
                        sb.append("\"").append(v).append("\" ");
                    }
                    sb.append("]\n");
                }
                return sb.toString();

    }

}
    