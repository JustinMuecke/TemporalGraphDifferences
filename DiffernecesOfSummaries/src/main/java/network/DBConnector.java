package network;

import com.orientechnologies.orient.core.db.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class DBConnector {

    private static String URL = "remote:localhost";
    private static String USERNAME = "admin";
    private static String PASSWORD = "admin";
    private static String serverUser = "root";
    private static String serverPassword = "rootpwd";


    public static Optional<List<Optional<ODatabaseSession>>> getDatabaseSessions(String database){
        List<Optional<ODatabaseSession>> sessionList = new LinkedList<>();
        OrientDB databaseServer = new OrientDB(URL, serverUser, serverPassword, OrientDBConfig.defaultConfig());

        for(int i = 29; i < 34; i++){
	    try{
            	sessionList.add(Optional.of(getSingleSession(databaseServer, database+"-"+i)));
            }
 	    catch(Exception e){
	        sessionList.add(Optional.empty());
	    }
	}
        return Optional.of(sessionList);
    }

    private static ODatabaseSession getSingleSession(OrientDB databaseServer, String database){
        ODatabasePool pool = new ODatabasePool(databaseServer, database, USERNAME, PASSWORD);
        return pool.acquire();
    }




}
