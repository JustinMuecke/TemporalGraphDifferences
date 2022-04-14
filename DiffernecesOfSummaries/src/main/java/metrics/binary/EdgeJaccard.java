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

<<<<<<< HEAD
         float numberOfEdgesInSection = graph1.getGraph().edgeSet().stream().distinct().filter(edge -> graph2.getGraph().edgeSet().contains(edge)).count();
         float numberOfEdgesInUnion = graph1.getGraph().edgeSet().size() + graph2.getGraph().edgeSet().size() - 2 * numberOfEdgesInSection;
=======
         float numberOfEdgesInSection = graph1.getGraph().distinctEdgeSet().stream().filter(edge -> graph2.getGraph().edgeSet().contains(edge)).count();
         float numberOfEdgesInUnion = graph1.getGraph().distinctEdgeSet().size() + graph2.getGraph().distinctEdgeSet().size() - 2 * numberOfEdgesInSection;
>>>>>>> f4b4c3f9f272e2583517e8cb66aa59730d91fc9a
	     float quotient =(numberOfEdgesInSection / numberOfEdgesInUnion);

         long compTime = System.currentTimeMillis() - start;
         return new JaccardEdgeResult(1-quotient, compTime, graph1.getName(), graph2.getName());
        } catch (Exception e){
            System.out.println("[Binary] Couldn't Calculate " + this.getClass());
            return null;
        }
    }
}
 
