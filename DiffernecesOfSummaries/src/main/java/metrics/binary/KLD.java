package metrics.binary;

import datamodel.ExtGraph;
import metrics.BinaryMetric;
import results.Result;
import results.binaryResults.KLDResult;

import java.util.List;
import java.util.stream.Collectors;

public class KLD implements BinaryMetric {

    @Override
    public Result compute(ExtGraph graph1, ExtGraph graph2) {
        long compStart = System.currentTimeMillis();
        float klu = 0;
        //Compute KL of all EQC which are in both timesteps.
        for(Integer eqc : graph1.getSecondaryIndex().keySet().stream().filter(eqc -> graph2.getSecondaryIndex().containsKey(eqc)).collect(Collectors.toList())) {
            float probTimeT1 =  computeProbabilityOfContainment(graph1, eqc);
            float probTimeT2 =  computeProbabilityOfContainment(graph2, eqc);
            klu += probTimeT1 * Math.log(probTimeT1/probTimeT2);
        }

        // Add Correction Terms
        float correctionDelete = 0;

        for(Integer eqc : graph1.getSecondaryIndex().keySet().stream().filter(eqc -> !graph2.getSecondaryIndex().containsKey(eqc)).collect(Collectors.toList())) {
            float probAtT = computeProbabilityOfContainment(graph1, eqc);
            correctionDelete -= probAtT * logDual(probAtT);
        }
        float correctionAdd = 0;
        for(Integer eqc : graph2.getSecondaryIndex().keySet().stream().filter(eqc -> !graph1.getSecondaryIndex().containsKey(eqc)).collect(Collectors.toList())) {
            float probAtT = computeProbabilityOfContainment(graph2, eqc);
            correctionDelete -= probAtT * logDual(probAtT);
        }

        //sum it up baby
        float kld = klu + correctionAdd + correctionDelete;
        long compTime = System.currentTimeMillis() - compStart;
        return new KLDResult(kld, compTime, graph1.getName(), graph2.getName());
    }

    private float computeProbabilityOfContainment(ExtGraph graph, Integer eqc){
            float sizeAtT = graph.getSecondaryIndex().get(eqc).length;
            float combinedSizeAtT = graph.getSecondaryIndex().values().stream().map(array -> array.length).reduce(0, Integer::sum);
            return sizeAtT/combinedSizeAtT;
    }

    private double logDual(double value){
        return Math.log(value) / Math.log(2);
    }
}


