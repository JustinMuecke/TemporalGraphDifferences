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
}
