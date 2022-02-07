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

    public void setIn(Integer in) {
        this.in = in;
    }

    public Integer getOut() {
        return out;
    }

    public void setOut(Integer out) {
        this.out = out;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "in=" + in +
                ", out=" + out +
                '}';
    }
}
