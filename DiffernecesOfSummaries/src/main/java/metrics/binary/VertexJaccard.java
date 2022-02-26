package metrics.binary;

import datamodel.ExtGraph;
import metrics.BinaryMetric;
import results.Result;
import results.binaryResults.JaccardVertexResult;

public class VertexJaccard implements BinaryMetric {
    @Override
    public Result compute(ExtGraph graph1, ExtGraph graph2) {
        long start = System.currentTimeMillis();
        long numberOfEQCInSection = graph1.getSecondaryIndex().keySet().stream().filter(key -> graph2.getSecondaryIndex().containsKey(key)).count();
        long numberOfEQCInUnion = (long) graph1.getSecondaryIndex().keySet().size() + graph2.getSecondaryIndex().keySet().stream().filter(key -> !graph1.getSecondaryIndex().containsKey(key)).count();
        long diff = numberOfEQCInSection/numberOfEQCInUnion;
        long compTime = System.currentTimeMillis() - start;
        return new JaccardVertexResult(diff, compTime, graph1.getName(), graph2.getName());
    }
}
