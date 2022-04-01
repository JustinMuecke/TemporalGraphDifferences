package metrics.binary;

import datamodel.ExtGraph;
import metrics.BinaryMetric;
import results.Result;
import results.binaryResults.KLDResult;

public class KLD implements BinaryMetric {

    @Override
    public Result compute(ExtGraph graph1, ExtGraph graph2) {
        System.out.println("[Binary] Calculating " + this.getClass());
        try {
            long compStart = System.currentTimeMillis();
            //Compute D(t,t+1)
            float d1 = divergence(graph1, graph2);
            //Compute D(t+1,t)
            float d2 = divergence(graph2, graph1);
            return new KLDResult(d1+d2, System.currentTimeMillis()- compStart, graph1.getName(), graph2.getName());
        } catch (Exception e){
            System.out.println("couldn't calculate KLD");
            e.printStackTrace();
            return null;
        }
    }

    private float computeProbabilityOfContainment(ExtGraph graph, Integer eqc){
            float sizeAtT = graph.getSecondaryIndex().get(eqc);
            float combinedSizeAtT = graph.getSecondaryIndex().values().stream().reduce(0, Integer::sum);
            return sizeAtT/combinedSizeAtT;
    }


    private float divergence(ExtGraph graph1, ExtGraph graph2){
        float sum = 0;
        for(Integer eq : graph2.getSecondaryIndex().keySet()){
            if(!graph1.getSecondaryIndex().containsKey(eq)) continue;
            float probAtT = computeProbabilityOfContainment(graph1, eq);
            float probAtT2 = computeProbabilityOfContainment(graph2, eq);
            sum += probAtT * logDual(probAtT/probAtT2);
        }
        return sum;
    }
    private float logDual(double value){
        return (float) (Math.log(value) / Math.log(2));
    }
}


