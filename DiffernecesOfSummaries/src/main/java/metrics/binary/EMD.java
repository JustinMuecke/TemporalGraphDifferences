package metrics.binary;

import com.rits.cloning.Cloner;
import datamodel.CostEdge;
import datamodel.Edge;
import datamodel.ExtGraph;
import datamodel.MultiGraph;
import metrics.BinaryMetric;
import org.jgrapht.Graph;
import org.jgrapht.graph.Multigraph;
import results.Result;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class EMD implements BinaryMetric {
    @Override
    public Result compute(ExtGraph graph1, ExtGraph graph2) {


        Cloner cloner = new Cloner();
        HashMap<Integer, Integer> workCopy = cloner.deepClone(graph2.getSecondaryIndex());
        Set<Integer> sources = new HashSet<>();
        Set<Integer> targets = new HashSet<>();


        //Find differences in Size
        for(Integer vertex : graph1.getSecondaryIndex().keySet()){
            if(graph2.getSecondaryIndex().containsKey(vertex)){
                workCopy.remove(vertex);
                int size1 = graph1.getSecondaryIndex().get(vertex);
                int size2 = graph2.getSecondaryIndex().get(vertex);
                if(size1 > size2){
                    targets.add(vertex);
                }
                if(size1 < size2){
                    sources.add(vertex);
                }
            }
            else {
                sources.add(vertex);
            }

        }
        //Remaining vertices in workCopy are newly added -> all targets
        targets.addAll(workCopy.keySet());


    }



    private int getDistance(ExtGraph extGraph, Integer source, Integer target){
        if(extGraph.getGraph().vertexSet().contains(source)){
            int inDiff = Math.abs(extGraph.getGraph().getInDegreeMap().get(source) - extGraph.getGraph().getInDegreeMap().get(target));
            int outDiff = Math.abs(extGraph.getGraph().getOutDegreeMap().get(source) - extGraph.getGraph().getOutDegreeMap().get(target));
            return inDiff + outDiff;
        }
        else{
            return extGraph.getGraph().getInDegreeMap().get(target) + extGraph.getGraph().getOutDegreeMap().get(target) + 1;
        }
    }


    private float successiveShortestPath(Graph<Integer, Edge> graph){

    }





}
