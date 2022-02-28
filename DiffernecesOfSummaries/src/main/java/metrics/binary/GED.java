package metrics.binary;

import datamodel.ExtGraph;
import metrics.BinaryMetric;
import results.Result;
import results.binaryResults.GEDResult;

public class GED implements BinaryMetric {
    @Override
    public Result compute(ExtGraph graph1, ExtGraph graph2) {
        long start = System.currentTimeMillis();
        long diff = 0;

        for (Integer vertex : graph1.getGraph().vertexSet()) {
            for (Integer vertex2 : graph2.getGraph().vertexSet()) {
                if (vertex.equals(vertex2)) {
                    diff += Math.abs(graph1.getGraph().outgoingEdgesOf(vertex).size() - graph2.getGraph().outgoingEdgesOf(vertex2).size());
                    diff += Math.abs(graph1.getGraph().incomingEdgesOf(vertex).size() - graph2.getGraph().outgoingEdgesOf(vertex2).size());

                }
                break;
            }
            diff += 1 + graph1.getGraph().outgoingEdgesOf(vertex).size() + graph1.getGraph().incomingEdgesOf(vertex).size();
        }
        for(Integer v1 : graph2.getGraph().vertexSet()){
            for(Integer v2 : graph1.getGraph().vertexSet()){
                if(v2.equals(v1)){
                    break;
                }
            }
            diff += 1 + graph2.getGraph().outgoingEdgesOf(v1).size() + graph2.getGraph().incomingEdgesOf(v1).size();
        }
        //This implementation is not usable, because .contains() does not return true even if the vertex exists in both graphs.
         /*
            if(graph2.getGraph().vertexSet().contains(vertex)){
                diff += Math.abs(graph1.getGraph().outgoingEdgesOf(vertex).size() - graph2.getGraph().outgoingEdgesOf(vertex).size());
                diff += Math.abs(graph1.getGraph().incomingEdgesOf(vertex).size() - graph2.getGraph().outgoingEdgesOf(vertex).size());
            }
            else{
                diff += 1 + graph1.getGraph().outgoingEdgesOf(vertex).size() + graph1.getGraph().incomingEdgesOf(vertex).size();
            }
        }
        for (Integer vertex : graph2.getGraph().vertexSet().stream().filter(vertex -> !graph1.getGraph().vertexSet().contains(vertex)).collect(Collectors.toList())){
            diff += 1 + graph2.getGraph().outgoingEdgesOf(vertex).size() + graph2.getGraph().incomingEdgesOf(vertex).size();
        */

        long compTime = System.currentTimeMillis() - start;
        return new GEDResult(diff, compTime, graph1.getName(), graph2.getName());
    }
}
