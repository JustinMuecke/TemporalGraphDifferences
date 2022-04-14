package metrics.binary;

import datamodel.ExtGraph;
import metrics.BinaryMetric;
import results.Result;
import results.binaryResults.JaccardEdgeResult;

public class EdgeJaccard implements BinaryMetric {
    @Override
    public Result compute(ExtGraph graph1, ExtGraph graph2) {
        System.out.println("[Binary] Calculating " + this.getClass());
        System.out.println("[Binary][EJ] EdgeSize Graph 1: " + graph1.getGraph().edgeSet().size());
        System.out.println("[Binary][EJ] EdgeSize Graph 2: " + graph2.getGraph().edgeSet().size());

        try{
        long start = System.currentTimeMillis();

         float numberOfEdgesInSection = graph1.getDistinctGraph().edgeSet().stream().filter(edge -> graph2.getGraph().edgeSet().contains(edge)).count();
         float numberOfEdgesInUnion = graph1.getDistinctGraph().edgeSet().size() + graph2.getDistinctGraph().edgeSet().size() - 2 * numberOfEdgesInSection;
	     float quotient =(numberOfEdgesInSection / numberOfEdgesInUnion);

         long compTime = System.currentTimeMillis() - start;
         return new JaccardEdgeResult(1-quotient, compTime, graph1.getName(), graph2.getName());
        } catch (Exception e){
            System.out.println("[Binary] Couldn't Calculate " + this.getClass());
            return null;
        }
    }
}
 
