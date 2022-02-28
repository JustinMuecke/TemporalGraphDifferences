package metrics.binary;

import datamodel.Edge;
import datamodel.ExtGraph;
import metrics.BinaryMetric;
import results.Result;
import results.binaryResults.JaccardEdgeResult;

public class EdgeJaccard implements BinaryMetric {
    @Override
    public Result compute(ExtGraph graph1, ExtGraph graph2) {
         long start = System.currentTimeMillis();
         if(graph1.getGraph().edgeSet().size() == 0 || graph2.getGraph().edgeSet().size() == 0)
             return null;
         System.out.println("TESTING EDGE SET");
         for(Edge e : graph1.getGraph().edgeSet()){
             System.out.print(e.toString() + "->");
             if(graph2.getGraph().edgeSet().contains(e)){
                 System.out.print("./ \n");
             }
             else{
                 System.out.print("x \n");
             }
         }
         float numberOfEdgesInSection = graph1.getGraph().edgeSet().stream().filter(edge -> graph2.getGraph().edgeSet().contains(edge)).count();
         float numberOfEdgesInUnion = graph1.getGraph().edgeSet().size() + graph2.getGraph().edgeSet().stream().filter(edge -> !graph1.getGraph().edgeSet().contains(edge)).count();
	     float quotient =1-(numberOfEdgesInSection / numberOfEdgesInUnion);
         long compTime = System.currentTimeMillis() - start;
         return new JaccardEdgeResult(quotient, compTime, graph1.getName(), graph2.getName());
    }
}
 
