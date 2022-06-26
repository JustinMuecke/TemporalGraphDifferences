package metrics.binary;

import com.rits.cloning.Cloner;
import datamodel.Edge;
import datamodel.ExtGraph;
import metrics.BinaryMetric;
import results.Result;
import results.binaryResults.JaccardEdgeResult;

import java.util.LinkedList;
import java.util.List;
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
            List<Edge> edgeClone1 = new LinkedList<>(graph1.getGraph().edgeSet());
            List<Edge> edgeClone2 = new LinkedList<>(graph2.getGraph().edgeSet());
            logger.info("[Binary] [EJ] Created Copies");
            List<Edge>  hitList = new LinkedList<>();

            float numberOfEdgesInSection = 0;
            for(Edge e1 : edgeClone1){
                for(Edge e2: edgeClone2){
                    if(e1.equals(e2)){
                        hitList.add(e1);
                        numberOfEdgesInSection++;
                        break;
                    }
                }
                edgeClone2.remove(hitList);
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
 
