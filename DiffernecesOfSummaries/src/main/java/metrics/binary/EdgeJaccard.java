package metrics.binary;

import datamodel.ExtGraph;
import metrics.BinaryMetric;
import results.Result;
import results.binaryResults.JaccardEdgeResult;

public class EdgeJaccard implements BinaryMetric {
    @Override
    public Result compute(ExtGraph graph1, ExtGraph graph2) {
	System.out.println("------- EDGE JACCARD ------");
         long start = System.currentTimeMillis();
         long numberOfEdgesInSection = graph1.getGraph().edgeSet().stream().filter(edge -> graph2.getGraph().edgeSet().contains(edge)).count();
         System.out.println("NAMES " + graph1.getName() + " - " + graph2.getName());
	 System.out.println("EDGE SET " + graph1.getGraph().edgeSet().toString());
	System.out.println("EDGE SIZE" + graph1.getGraph().edgeSet().size());
	 long numberOfEdgesInUnion = graph1.getGraph().edgeSet().size() + graph2.getGraph().edgeSet().stream().filter(edge -> !graph1.getGraph().edgeSet().contains(edge)).count();
         System.out.println(graph2.getGraph().edgeSet().stream().filter(edge -> !graph1.getGraph().edgeSet().contains(edge)).count());
	 long quotient =1-(numberOfEdgesInSection / numberOfEdgesInUnion);
         long compTime = System.currentTimeMillis() - start;
         return new JaccardEdgeResult(quotient, compTime, graph1.getName(), graph2.getName());
    }
}
 
