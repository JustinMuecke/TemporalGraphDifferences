package datamodel;

public class CostEdge extends Edge{

    private int cap;
    private int distance;

    public CostEdge(Integer in, Integer out, int cap, int distance){
        super(in, out);
        this.cap = cap;
        this.distance = distance;
    }

    public int getCap() {
        return cap;
    }

    public int getDistance() {
        return distance;
    }
}
