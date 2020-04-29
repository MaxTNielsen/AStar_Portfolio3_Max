package sample;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
Assignment by Max T. Nielsen
 **/

public class AStarGraph {

    private ArrayList<Vertex> vertices;

    private static AStarGraph instance = new AStarGraph();

    private AStarGraph() {
        vertices = new ArrayList<>();
    }

    public static AStarGraph getInstance() {
        return instance;
    }

    public ArrayList<Vertex> getVertices() {
        return this.vertices;
    }


    public void addvertex(Vertex v) {
        vertices.add(v);
    }

    public void newconnection(Vertex v1, Vertex v2, Double dist) {
        v1.addOutEdge(v2, dist); //adds outedge to both edges as it is undirectional
        v2.addOutEdge(v1, dist);
    }

    public boolean A_Star(Vertex start, Vertex destination) {

        if (start == null || destination == null) return false; // O(1)

        PriorityQueue<Vertex> Openlist = new PriorityQueue<Vertex>(); // O(1)

        ArrayList<Vertex> Closedlist = new ArrayList<Vertex>(); // O(1)

        Vertex Current; // O(1)

        Vertex Neighbor; // O(1)

        //Initialize h with chosen heuristic
        for (int i = 0; i < vertices.size(); i++) { // O(V)
            vertices.get(i).seth(Manhattan(vertices.get(i), destination)); // O(1)
        }

        start.setg(0.0); // O(1)
        start.calculatef(); // O(1)

        Openlist.offer(start); // O(1)

        long starTime = System.nanoTime();

        while (!Openlist.isEmpty()) { // O(|V|)
            Current = Openlist.poll(); // O(log |V|)
            if (Current == destination) {
                resetWeights();
                long endTime = System.nanoTime();
                long elapsedTime = endTime - starTime;
                System.out.println(elapsedTime + "\n");
                return true; // O(1)
            }
            Closedlist.add(Current); // O(1)
            for (int i = 0; i < Current.getNeighbours().size(); i++) { // O(|E|)
                Neighbor = Current.getNeighbours().get(i); // O(1)
                Double tempgofv = Current.getg() + Current.getNeighbourDistance().get(i);
                // O(1)
                if (tempgofv < Neighbor.getg()) { // O(1)
                    Neighbor.setPrev(Current); // O(1)
                    Neighbor.setg(tempgofv); // O(1)
                    Neighbor.calculatef(); // O(1)
                    if (!Closedlist.contains(Neighbor) && !Openlist.contains(Neighbor)) {
                        // O(|V|)
                        Openlist.offer(Neighbor); //O(log |V|)
                    } else if (Openlist.contains(Neighbor)) { // O(|V|) Ensuring priority is updated
                        Openlist.remove(Neighbor); // O(|V|)
                        Openlist.offer(Neighbor); // O(log |V|)
                    }
                }
            }
        }

        return false;
    }

    public Double Manhattan(Vertex from, Vertex goal) {
        //Implement this
        return Double.valueOf(Math.abs(goal.getx() - from.getx()) + Math.abs(goal.gety() - from.gety()));
    }

    public Double Euclidean(Vertex from, Vertex to) {

        Double fx = Double.valueOf(from.getx());
        Double fy = Double.valueOf(from.gety());
        Double tx = Double.valueOf(to.getx());
        Double ty = Double.valueOf(to.gety());
        Double fxtx = tx - fx;
        Double fyty = ty - fy;
        Double squaredxs = Math.pow(fxtx, 2);
        Double squaredys = Math.pow(fyty, 2);

        return Math.sqrt(squaredxs + squaredys);
    }

    public void resetWeights(){ //resetting the weights
        for (Vertex vertex : vertices) {
            vertex.setg(Double.POSITIVE_INFINITY);
            vertex.setf(Double.POSITIVE_INFINITY);
        }
    }
}

class Vertex implements Comparable<Vertex> {
    private String id;
    private ArrayList<Vertex> Neighbours = new ArrayList<Vertex>();
    private ArrayList<Double> NeighbourDistance = new ArrayList<Double>();
    private Double f; //sum of g + h
    private Double g; //weighted cost
    private Double h; //estimate from vertex to goal
    private Integer x;
    private Integer y;
    private Vertex prev = null;

    public Vertex(String id, int x_cor, int y_cor) {
        this.id = id;
        this.x = x_cor;
        this.y = y_cor;
        f = Double.POSITIVE_INFINITY;
        g = Double.POSITIVE_INFINITY;
        h = 0.0;
    }

    public void addOutEdge(Vertex toV, Double dist) {
        Neighbours.add(toV);
        NeighbourDistance.add(dist);
    }

    public ArrayList<Vertex> getNeighbours() {
        return Neighbours;
    }

    public ArrayList<Double> getNeighbourDistance() {
        return NeighbourDistance;
    }

    public String getid() {
        return id;
    }

    public Integer getx() {
        return x;
    }

    public Integer gety() {
        return y;
    }

    public Double getf() {
        return f;
    }

    public void calculatef() {
        f = g + h;
    }

    public void setf(Double newf) {f = newf; }

    public Double getg() {
        return g;
    }

    public void setg(Double newg) {
        g = newg;
    }

    public Double geth() {
        return h;
    }

    public void seth(Double estimate) {

        h = estimate;
    }

    public Vertex getPrev() {
        return prev;
    }

    public void setPrev(Vertex v) {

        prev = v;
    }

    @Override
    public int compareTo(Vertex o) { //comparator method to decide ordering
        if (this.getf() > (o.getf()) + 0.0001) return 1;
        if (this.getf() < (o.getf()) + 0.0001) return -1;
        return 0;
    }
}
