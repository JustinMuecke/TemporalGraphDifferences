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
            System.out.println("[Binary] Vertex Set 1: " + graph1.getSecondaryIndex().keySet().size());
            System.out.println("[Binary] Vertex Set 2: " + graph2.getSecondaryIndex().keySet().size());
            float inSection = graph1.getSecondaryIndex().keySet().stream().filter(key -> graph2.getSecondaryIndex().containsKey(key)).count();
            float inUnion =  graph1.getSecondaryIndex().keySet().size() + graph2.getSecondaryIndex().keySet().size() - 2 * inSection;
            float diff = inSection/inUnion;
            long compTime = System.currentTimeMillis() - start;
            return new JaccardVertexResult(1 - diff, compTime, graph1.getName(), graph2.getName());
        } catch (Exception e){
            System.out.println("[Binary] Couldn't Calculate " + this.getClass());
            return null;
        }
    }
}
