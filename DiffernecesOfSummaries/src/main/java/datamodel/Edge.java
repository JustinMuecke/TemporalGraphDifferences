package datamodel;

public class Edge {
    private Integer in;
    private Integer out;

    public Edge(Integer in, Integer out) {
        this.in = in;
        this.out = out;
    }

    public Edge(){
    }

    public Integer getIn() {
        return in;
    }

    public Integer getOut() {
        return out;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "in=" + in +
                ", out=" + out +
                '}';
    }
    @Override
    public boolean equals(Object object){
        if(object==null){
            return false;
        }
        if(object.getClass() != this.getClass()){
            return false;
        }
        Edge e = (Edge) object;
        return this.getIn().equals(e.getIn()) && this.getOut().equals(e.getOut());
    }

    @Override
    public int hashCode(){
        int hash = 17;
        hash = hash * 31 + this.getIn();
        hash = hash * 31 + this.getOut();
        return hash;
    }
}
