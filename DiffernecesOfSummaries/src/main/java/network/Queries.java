package network;

import com.orientechnologies.orient.core.db.ODatabaseSession;
import datamodel.Edge;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Queries {


    public static Optional<List<Edge>> getEdges(ODatabaseSession databaseSession){
        databaseSession.activateOnCurrentThread();
        return Optional.of(
                databaseSession.query("SELECT in.hash, out.hash FROM (SELECT in, out FROM E)")
                        .stream()
                        .map(result -> new Edge(result.getProperty("in.hash"), result.getProperty("out.hash")))
                        .collect(Collectors.toList()));
    }
    public static Optional<List<Integer>> getVertices(ODatabaseSession databaseSession){
        databaseSession.activateOnCurrentThread();
        return Optional.of(
                (databaseSession.query("SELECT * FROM V")
                        .stream()
                        .mapToInt(result -> result.getProperty("hash"))
                        .boxed().collect(Collectors.toList())
                ));
    }
}
