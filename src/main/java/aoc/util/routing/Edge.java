package aoc.util.routing;

import java.util.Objects;

public class Edge {

    private long weight;
    private Node from;
    private Node to;

    public Edge(long weight, Node from, Node to) {
        this.weight = weight;
        this.from = from;
        this.to = to;
    }

    public long getWeight() {
        return weight;
    }

    public Node getFrom() {
        return from;
    }

    public Node getTo() {
        return to;
    }

    @Override
    public String toString() {
        return from +"->" + to + "(" + weight + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return weight == edge.weight && from == edge.from && to == edge.to;
    }

    @Override
    public int hashCode() {
        return Objects.hash(weight, from, to);
    }
}
