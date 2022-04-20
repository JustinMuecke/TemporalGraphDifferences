package metrics.binary;

import com.rits.cloning.Cloner;
import datamodel.Edge;
import datamodel.ExtGraph;
import metrics.BinaryMetric;
import results.Result;
import results.binaryResults.JaccardEdgeResult;

import java.util.logging.Logger;

public class EdgeJaccard implements BinaryMetric {

    private final static Logger logger = Logger.getLogger(Edge.class.getName());
    @Override
    public Result compute(ExtGraph graph1, ExtGraph graph2) {
        System.out.println("[Binary] Calculating " + this.getClass());
        System.out.println("[Binary] [EJ] EdgeSize Graph 1: " + graph1.getDistinctGraph().edgeSet().size());
        System.out.println("[Binary] [EJ] EdgeSize Graph 2: " + graph2.getDistinctGraph().edgeSet().size());

        try{

            long start = System.currentTimeMillis();

            Cloner cloner = new Cloner();
            ExtGraph clone1 = cloner.deepClone(graph1);
            ExtGraph clone2 = cloner.deepClone(graph2);
            logger.info("[Binary] [EJ] Created Copies");

            float numberOfEdgesInSection = 0;
            for(Edge e1 : clone1.getGraph().edgeSet()){
                for(Edge e2: clone2.getGraph().edgeSet()){
                    if(e1.equals(e2)){
                        clone1.getGraph().edgeSet().remove(e1);
                        clone2.getGraph().edgeSet().remove(e2);
                        numberOfEdgesInSection++;
                        break;
                    }
                }
            }


         //float numberOfEdgesInSection = graph1.getDistinctGraph().edgeSet().stream().filter(edge -> graph2.getDistinctGraph().edgeSet().contains(edge)).count();
         float numberOfEdgesInUnion = graph1.getDistinctGraph().edgeSet().size() + graph2.getDistinctGraph().edgeSet().size() - numberOfEdgesInSection;
         float quotient =(numberOfEdgesInSection / numberOfEdgesInUnion);


         long compTime = System.currentTimeMillis() - start;
         return new JaccardEdgeResult(1-quotient, compTime, graph1.getName(), graph2.getName());
        } catch (Exception e){
            System.out.println("[Binary] Couldn't Calculate " + this.getClass());
            return null;
        }
    }
}
 
