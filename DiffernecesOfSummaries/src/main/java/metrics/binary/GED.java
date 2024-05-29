package metrics.binary;

import datamodel.ExtGraph;
import metrics.BinaryMetric;
import results.Result;
import results.binaryResults.GEDResult;

import java.util.stream.Collectors;

public class GED implements BinaryMetric {

    @Override
    public Result compute(ExtGraph graph1, ExtGraph graph2) {
        System.out.println("[Binary] Calculating " + this.getClass());
        try {
            long start = System.currentTimeMillis();
            float diff = 0;
            for (Integer vertex : graph1.getGraph().vertexSet()){
                if(graph2.getGraph().vertexSet().contains(vertex)){
                    diff += Math.abs(graph1.getGraph().getOutDegreeMap().get(vertex) - graph2.getGraph().getOutDegreeMap().get(vertex));
                    diff += Math.abs(graph1.getGraph().getInDegreeMap().get(vertex) - graph2.getGraph().getInDegreeMap().get(vertex));
                }
                else{
                    diff += 1 + graph1.getGraph().getOutDegreeMap().get(vertex) + graph1.getGraph().getInDegreeMap().get(vertex);
                }
            }
            for (Integer vertex : graph2.getGraph().vertexSet().stream().filter(vertex -> !graph1.getGraph().vertexSet().contains(vertex)).collect(Collectors.toList())) {
                diff += 1 + graph2.getGraph().getOutDegreeMap().get(vertex) + graph2.getGraph().getInDegreeMap().get(vertex);
            }

            long compTime = System.currentTimeMillis() - start;
            return new GEDResult(diff, compTime, graph1.getName(), graph2.getName());
        }catch (Exception e){
            System.out.println("[Binary] Couldn't Calculate " + this.getClass());
            return null;
        }
    }
}
