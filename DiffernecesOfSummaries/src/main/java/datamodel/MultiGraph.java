package datamodel;

import org.jgrapht.GraphType;

import java.util.*;

public class MultiGraph{


    List<Integer> vertexSet;
    List<Edge> edgeSet;
    Map<Integer, Integer> degreeMap;
    Set<Edge> distinctEdgeSet;

    public MultiGraph() {
        this.vertexSet = new LinkedList<Integer>();
        this.edgeSet = new LinkedList<Edge>();
        this.degreeMap = new HashMap<>();
        this.distinctEdgeSet = new HashSet<>();
    }

    public List<Edge> getAllEdges(Object sourceVertex, Object targetVertex) {
        return this.edgeSet;
    }


    public Object getEdge(Object sourceVertex, Object targetVertex) {
        return null;
    }


    public Object addEdge(Object sourceVertex, Object targetVertex) {
        java.lang.Integer in = (java.lang.Integer) sourceVertex;
        java.lang.Integer out = (java.lang.Integer) targetVertex;

        this.edgeSet.add(new Edge(in, out));
        return null;
    }

    public boolean addEdge(Object sourceVertex, Object targetVertex, Object o) {
        java.lang.Integer in = (java.lang.Integer) sourceVertex;
        java.lang.Integer out = (java.lang.Integer) targetVertex;
        this.edgeSet.add(new Edge(in, out));
        this.distinctEdgeSet.add(new Edge(in, out));
        this.degreeMap.replace(in, degreeMap.get(in) + 1);
        this.degreeMap.replace(out, degreeMap.get(out) + 1);
        return true;
    }

    public Object addVertex() {
        return null;
    }

    public boolean addVertex(Integer o) {
        this.vertexSet.add(o);
        this.degreeMap.put(o, 0);
        return true;
    }

    public boolean containsEdge(Object sourceVertex, Object targetVertex) {
        return false;
    }

    public boolean containsEdge(Object o) {
        return false;
    }

    public boolean containsVertex(Object o) {
        return vertexSet.contains((Integer) o);
    }

    public List<Edge> edgeSet() {
        return this.edgeSet;
    }

    public Set<Edge> distinctEdgeSet(){
        return this.distinctEdgeSet;
    }

    public int degreeOf(Integer vertex) {
        return (int) edgeSet.stream().filter(e -> e.getIn().equals(vertex) || e.getOut().equals(vertex)).count();
    }

    public Set edgesOf(Object vertex) {
        return null;
    }

    public int inDegreeOf(Object vertex) {
        return 0;
    }

    public Set incomingEdgesOf(Object vertex) {
        return null;
    }

    public int outDegreeOf(Object vertex) {
        return 0;
    }

    public Set outgoingEdgesOf(Object vertex) {
        return null;
    }

    public boolean removeAllEdges(Collection edges) {
        return false;
    }

    public Set removeAllEdges(Object sourceVertex, Object targetVertex) {
        return null;
    }

    public boolean removeAllVertices(Collection vertices) {
        return false;
    }

    public Object removeEdge(Object sourceVertex, Object targetVertex) {
        return null;
    }

    public boolean removeEdge(Object o) {
        return false;
    }

    public boolean removeVertex(Object o) {
        return false;
    }

    public List<Integer> vertexSet() {
        return this.vertexSet;
    }

    public Object getEdgeSource(Object o) {
        return null;
    }

    public Object getEdgeTarget(Object o) {
        return null;
    }

    public GraphType getType() {
        return null;
    }


    public double getEdgeWeight(Object o) {
        return 0;
    }

    public void setEdgeWeight(Object o, double weight) {

    }

    public Map<Integer, Integer> getDegreeMap() {
        return degreeMap;
    }
}
