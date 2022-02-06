package network;

import com.orientechnologies.orient.core.db.ODatabaseSession;

import java.util.List;
import java.util.Optional;

public class Queries {

    public static long getNumberOfVertices(ODatabaseSession databaseSession){
        databaseSession.activateOnCurrentThread();
        return databaseSession.query("SELECT hash FROM V").vertexStream().count();
    }

    public static long getNumberOfEdges(ODatabaseSession databaseSession){
        databaseSession.activateOnCurrentThread();
        return databaseSession.query("SELECT in,out FROM E").elementStream().count();
    }


    public static Optional<List<Integer>> getVertices(ODatabaseSession databaseSession){
        databaseSession.activateOnCurrentThread();
        return Optional.of(
                (databaseSession.query("SELECT * FROM V")
                        .stream()
                        .mapToInt(result -> result.getProperty("hash"))
                        .boxed().toList()
                ));
    }
}
