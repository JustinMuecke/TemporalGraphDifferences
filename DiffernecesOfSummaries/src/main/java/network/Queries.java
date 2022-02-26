package network;

import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.sql.executor.OResult;
import com.orientechnologies.orient.core.sql.executor.OResultSet;
import datamodel.Edge;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Queries {


    public static Optional<List<Edge>> getEdges(ODatabaseSession databaseSession){
        databaseSession.activateOnCurrentThread();
        OResultSet resultSet = databaseSession.query("SELECT in.hash, out.hash FROM (SELECT in, out FROM E)");
        Stream<OResult> resultStream = resultSet.stream();
        Stream<Edge> edgeStream = resultStream.map(result -> new Edge(result.getProperty("in.hash"), result.getProperty("out.hash")));
        List<Edge> edgeList = edgeStream.collect(Collectors.toList());

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
