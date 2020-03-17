package astar;

import java.util.ArrayList;
import java.util.List;

public class Spot {

    private int x;
    private int y;
    public boolean wall;

    private double f;
    private double g;
    private double h;
    private List<Spot> neighbors;
    private Spot previous;


    public Spot(int x, int y, boolean wall) {
        this.x = x;
        this.y = y;
        this.wall = wall;

        this.f = 0;
        this.g = 0;
        this.h = 0;
        this.neighbors = new ArrayList<>();
        this.previous = null;

    }

    public void setPrevious(Spot previous) {
        this.previous = previous;
    }

    public Spot getPrevious() {
        return this.previous;
    }

    public void addNeighbor(Spot neighbor) {
        if (!this.equals(neighbor)) {
            this.neighbors.add(neighbor);
        }
    }

    public List<Spot> getNeighbors() {
        return this.neighbors;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getF() {
        return f;
    }

    public void updateF() {
        this.f = this.g + this.h;
    }

    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;

    }

    public boolean equals(Spot b) {
        return this.x == b.x && this.y == b.y;
    }

}
