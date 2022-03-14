package metrics.binary;

import datamodel.Edge;
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
         if(graph1.getGraph().edgeSet().size() == 0 || graph2.getGraph().edgeSet().size() == 0)
             return null;
         float numberOfEdgesInSection = 0;
         float numberOfEdgesInUnion = graph2.getGraph().edgeSet().size();
         for(Edge e1 : graph1.getGraph().edgeSet()){
             for (Edge e2 : graph2.getGraph().edgeSet()){
                 if(e1.getOut().equals(e2.getOut()) && e1.getIn().equals(e2.getIn())){
                     numberOfEdgesInSection ++;
                     break;
                 }
             }
             numberOfEdgesInUnion++;
         }
         //float numberOfEdgesInSection = graph1.getGraph().edgeSet().stream().filter(edge -> graph2.getGraph().edgeSet().contains(edge)).count();
         //float numberOfEdgesInUnion = graph1.getGraph().edgeSet().size() + graph2.getGraph().edgeSet().stream().filter(edge -> !graph1.getGraph().edgeSet().contains(edge)).count();
	     float quotient =1-(numberOfEdgesInSection / numberOfEdgesInUnion);

         long compTime = System.currentTimeMillis() - start;
         return new JaccardEdgeResult(quotient, compTime, graph1.getName(), graph2.getName());
        } catch (Exception e){
            System.out.println("[Binary] Couldn't Calculate " + this.getClass());
            return null;
        }
    }
}
 
