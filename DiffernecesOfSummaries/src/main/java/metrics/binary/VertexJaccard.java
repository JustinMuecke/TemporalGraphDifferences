package metrics.binary;

import datamodel.ExtGraph;
import metrics.BinaryMetric;
import results.Result;
import results.binaryResults.JaccardVertexResult;

import java.util.Objects;

public class VertexJaccard implements BinaryMetric {

    @Override
    public Result compute(ExtGraph graph1, ExtGraph graph2) {
        System.out.println("[Binary] Calculating " + this.getClass());
        try{

        long start = System.currentTimeMillis();
        float inSection = 0;
        float inUnion = graph2.getGraph().edgeSet().size();
        for(Integer v1 : graph1.getGraph().vertexSet()){
            for(Integer v2 : graph2.getGraph().vertexSet()){
                if(Objects.equals(v2, v1)){
                    inSection ++;
                    break;
                }
            }
            inUnion++;
        }


        //long numberOfEQCInSection = graph1.getSecondaryIndex().keySet().stream().filter(key -> graph2.getSecondaryIndex().containsKey(key)).count();
        //long numberOfEQCInUnion = (long) graph1.getSecondaryIndex().keySet().size() + graph2.getSecondaryIndex().keySet().stream().filter(key -> !graph1.getSecondaryIndex().containsKey(key)).count();
        float diff = inSection/inUnion;
        long compTime = System.currentTimeMillis() - start;
        return new JaccardVertexResult(1 - diff, compTime, graph1.getName(), graph2.getName());
        } catch (Exception e){
            System.out.println("[Binary] Couldn't Calculate " + this.getClass());
            return null;
        }
    }
}
