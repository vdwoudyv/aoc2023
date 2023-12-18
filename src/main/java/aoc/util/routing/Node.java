package aoc.util.routing;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Node {


    private long bestDistanceToOrigin = 1000000;
    private List<Edge> outgoing = new ArrayList<>();
    private List<Edge> incoming = new ArrayList<>();
    protected String name;

    protected Node() {
    }

    public Node(String name) {
        this.name = name;
    }

    public void addEdge(Edge ed) {
        if (ed.getFrom() == this) {
            outgoing.add(ed);
        } else if (ed.getTo() == this) {
            incoming.add(ed);
        } else {
            throw new RuntimeException("Invalid edge");
        }
    }

    public List<Edge> getIncomingEdges() {
        return incoming;
    }

    public List<Edge> getOutgoingEdges() {
        return outgoing;
    }

    public long getBestDistanceToOrigin() {
        return bestDistanceToOrigin;
    }

    public boolean updateBestDistance() {
        boolean changed = false;
        for (Edge e: getIncomingEdges()) {
            long updated = e.getFrom().getBestDistanceToOrigin() + e.getWeight();
            if (updated < getBestDistanceToOrigin()) {
                changed = true;
                bestDistanceToOrigin = updated;
            }
        }
        return changed;
    }

    public void setBestDistanceToOrigin(long bestDistanceToOrigin) {
        this.bestDistanceToOrigin = bestDistanceToOrigin;
    }

    public void removeEdge(Edge edgeToRemove) {
        incoming.remove(edgeToRemove);
        outgoing.remove(edgeToRemove);
    }

    public void removeAllConnectionsWith(Node other) {
        incoming = incoming.stream().filter(e -> e.getTo() != other && e.getFrom() != other).collect(Collectors.toList());
        outgoing = outgoing.stream().filter(e -> e.getTo() != other && e.getFrom() != other).collect(Collectors.toList());
    }

    public void reset() {
        bestDistanceToOrigin = 1000000;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }
}
