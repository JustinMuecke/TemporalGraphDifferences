package results.binaryResults;


import datamodel.Edge;
import datamodel.ExtGraph;
import metrics.BinaryMetric;
import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import results.Result;

import java.util.*;

public class EMD implements BinaryMetric {
    @Override
    public Result compute(ExtGraph graph1, ExtGraph graph2) {

        //Compute all components
        ConnectivityInspector<Integer, Edge> connectivityInspector = new ConnectivityInspector<>(graph2.getGraph());
        List<Set<Integer>> vertexComponents = connectivityInspector.connectedSets();

        List<Graph<Integer, Edge>> graphComponents = convertVertexSetToGraph(graph2.getGraph(), vertexComponents);
        if(graphComponents == null){
            return null;
        }

        for (Graph<Integer, Edge> component : graphComponents){
            HashMap<Integer, Integer> sourceNodes = getFlowNodes(component, graph1, graph2);
            HashMap<Integer, Integer> sinkNodes = getFlowNodes(component, graph2, graph1);
        }

        return null;
    }

    /**
     * Calculates all source nodes if graph1 represent source graph at time t and graph2 represents time t+1.
     * Calculates all sink nodes if graph2 represents source graph at time t+1 and graph2 represents time t
     * @param component graph of which the flow needs to be calculated
     * @param graph1 source Graph at time t
     * @param graph2 source Graph at time t+1
     * @return Map (Hashcode of EQC, Size of generated Flow)
     */
    private HashMap<Integer, Integer> getFlowNodes(Graph<Integer, Edge> component, ExtGraph graph1, ExtGraph graph2){
        HashMap<Integer, Integer> sourceNodes = new HashMap<>();
        for(Integer vertex : component.vertexSet()) {
            //not in First Graph
            if (!graph1.getSecondaryIndex().containsKey(vertex)) {
                continue;
            }
            //If decreases in size, add to sources
            if (graph2.getSecondaryIndex().containsKey(vertex)) {
                if (graph1.getSecondaryIndex().get(vertex).length > graph2.getSecondaryIndex().get(vertex).length) {
                    sourceNodes.put(vertex, Math.abs(graph1.getSecondaryIndex().get(vertex).length - graph2.getSecondaryIndex().get(vertex).length));
                }
                continue;
            }
            sourceNodes.put(vertex, graph1.getSecondaryIndex().get(vertex).length);
        }
        return sourceNodes;
    }


    private float minCostOfFlow(Graph<Integer,Edge> graph, List<Integer> sourceNodes, List<Integer> sinkNodes){
        //get shortest path from each source to each sink
        //order paths from lowest value to highest value
        //sort flow from largest to lowest
        //satisfy largest first
        //satisfy smallest least.



        return 0;
    }

    private List<Graph<Integer, Edge>> convertVertexSetToGraph(Graph<Integer, Edge> graph, List<Set<Integer>> vertexComponents){

        return null;
    }
}
