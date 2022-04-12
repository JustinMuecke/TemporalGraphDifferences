package metrics.binary;

import datamodel.ExtGraph;
import metrics.BinaryMetric;
import results.Result;
import results.binaryResults.JaccardVertexResult;

public class VertexJaccard implements BinaryMetric {

    @Override
    public Result compute(ExtGraph graph1, ExtGraph graph2) {
        System.out.println("[Binary] Calculating " + this.getClass());
        try{

        long start = System.currentTimeMillis();
        long inSection = graph1.getSecondaryIndex().keySet().stream().filter(key -> graph2.getSecondaryIndex().containsKey(key)).count();
        long inUnion = (long) graph1.getSecondaryIndex().keySet().size() + graph2.getSecondaryIndex().keySet().stream().filter(key -> !graph1.getSecondaryIndex().containsKey(key)).count();
        float diff = inSection/inUnion;
        long compTime = System.currentTimeMillis() - start;
        return new JaccardVertexResult(1 - diff, compTime, graph1.getName(), graph2.getName());
        } catch (Exception e){
            System.out.println("[Binary] Couldn't Calculate " + this.getClass());
            return null;
        }
    }
}
