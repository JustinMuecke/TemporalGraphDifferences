import datamodel.Edge;
import org.jgrapht.Graph;
import org.jgrapht.graph.DirectedMultigraph;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions;

import java.util.Objects;

public class JaccardEdgeTest {

    @BeforeEach
    void setup(){

    }

    @Test
    void streamTest(){

        Graph<Integer, Edge> testGraph = new DirectedMultigraph<>(Edge.class);
        testGraph.addVertex(1);
        testGraph.addVertex(2);
        testGraph.addEdge(1,2, new Edge(1,2));

        Edge e = new Edge(1,2);
        for(Edge e1 : testGraph.edgeSet()){
            System.out.println(e.equals(e1));
            System.out.println(Objects.equals(e, e1));
            System.out.println(e1);
        }
        System.out.println(e);
        System.out.println(testGraph.edgeSet().contains(e));
        Assertions.assertTrue(testGraph.edgeSet().contains(e));

    }
}
