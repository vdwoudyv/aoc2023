package aoc.util.routing;

import java.util.*;

public class World<T extends Node> {

    private List<T> nodes;

    public World(List<T> nodes) {
        this.nodes = nodes;
    }

    /**
     * Rather naive shortest path implementation
     */
    public List<Edge> getRoute(T start, T stop) {
        List<Edge> edges = new ArrayList<>();
        for (Node n: nodes) {
            n.reset();
        }
        start.setBestDistanceToOrigin(0);
        boolean changed;
        do {
            changed = false;
            for (Node n : nodes) {
                changed = changed || n.updateBestDistance();
            }
        } while (changed);

        Node current = stop;
        while (current != start) {
            Optional<Edge> relevant = current.getIncomingEdges().stream()
                    .filter(e->e.getFrom().getBestDistanceToOrigin() + e.getWeight() == e.getTo().getBestDistanceToOrigin())
                    .findFirst();
            if (relevant.isPresent()) {
                edges.add(0, relevant.get());
                current = relevant.get().getFrom();
            } else {
                throw new RuntimeException("Unknown edge");
            }
        }
        return edges;
    }


    /**
     * Standard dijkstra
     */
    public List<Edge> getRouteDs(T start, T stop) {
        Set<Node> knownShortestPaths = new HashSet<>();
        List<Node> knownPaths = new ArrayList<>();

        // Initial Set
        start.setBestDistanceToOrigin(0);
        knownShortestPaths.add(start);
        for (Edge e: start.getOutgoingEdges()) {
            Node neighbour = e.getTo();
            neighbour.setBestDistanceToOrigin(e.getWeight());
            knownPaths.add(neighbour);
        }

        // main loop
        while (!knownPaths.isEmpty()) {
            knownPaths.sort((n1, n2) -> (int) (n1.getBestDistanceToOrigin() - n2.getBestDistanceToOrigin()));
            Node next = knownPaths.remove(0);
            knownShortestPaths.add(next);
            for (Edge e: next.getOutgoingEdges()) {
                Node neighbour = e.getTo();

                if (!knownShortestPaths.contains(neighbour)) {
                    long newDistance = Math.min(next.getBestDistanceToOrigin() + e.getWeight(), neighbour.getBestDistanceToOrigin());
                    neighbour.setBestDistanceToOrigin(newDistance);
                    if (!knownPaths.contains(neighbour)) {
                        knownPaths.add(neighbour);
                    }
                }
            }
        }
        List<Edge> edges = new ArrayList<>();
        Node current = stop;
        while (current != start) {
            Optional<Edge> relevant = current.getIncomingEdges().stream()
                    .filter(e->e.getFrom().getBestDistanceToOrigin() + e.getWeight() == e.getTo().getBestDistanceToOrigin())
                    .findFirst();
            if (relevant.isPresent()) {
                edges.add(0, relevant.get());
                current = relevant.get().getFrom();
            } else {
                throw new RuntimeException("Unknown edge");
            }
        }
        return edges;
    }




    public List<T> getNodes() {
        return nodes;
    }

    /**
     * Removes a node from the world. It will connect all incoming edges with all outgoing edges and add their weights.
     * So, suppose I have following structure (all edges weight 1):
     * A->C
     * A->D
     * B->C
     * C->B
     * C->D
     * C->E
     * E->C
     *
     * And I remove node C. I will retain nodes A,B,D and E with following routes
     * A->B (weight two)
     * A->D (weight two)
     * A->D (weight one)
     * A->E (weight two)
     * B->D (weight two)
     * B->E (weight two)
     * E->D (weight two)
     * E->B (weight two)
     *
     * Edges from a node to itself are not added, but multiple edges between the same nodes can be, as long as they have
     * different weight.
     * @param toRemove the node to remove
     */
    public void removeNode(T toRemove) {
        boolean wasPresent = nodes.remove(toRemove);
        if (wasPresent) {
            List<Edge> outgoing = toRemove.getOutgoingEdges();
            List<Edge> incoming = toRemove.getIncomingEdges();
            for (Edge in: incoming) {
                Node from = in.getFrom();
                for (Edge out: outgoing) {
                    Node to = out.getTo();
                    if (from != to) {
                        from.addEdge(new Edge(in.getWeight() + out.getWeight(), from, to));
                        to.addEdge(new Edge(in.getWeight() + out.getWeight(), from, to));
                    }
                }
            }
            for (Edge in: incoming) {
                toRemove.removeEdge(in);
                in.getFrom().removeAllConnectionsWith(toRemove);
            }
            for (Edge out: outgoing) {
                toRemove.removeEdge(out);
                out.getTo().removeAllConnectionsWith(toRemove);
            }
        }
    }

    public void reset() {
        for (Node n: nodes) {
            n.reset();
        }
    }
}
