package network;

import com.orientechnologies.orient.core.db.ODatabaseSession;

public class Queries {

    public static long getNumberOfVertices(ODatabaseSession databaseSession){
        databaseSession.activateOnCurrentThread();
        return databaseSession.query("SELECT * FROM V").vertexStream().count();
    }

    public static long getNumberOfEdges(ODatabaseSession databaseSession){
        databaseSession.activateOnCurrentThread();
        return databaseSession.query("SELECT * FROM E").elementStream().count();
    }
}
