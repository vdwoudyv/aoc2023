package aoc.day18;

import aoc.util.Coordinate;
import aoc.util.Direction;

public class Tunnel {

    private Coordinate location;
    private TunnelType type;
    private Direction outDirection;
    private String edgeColor;

    private boolean startNode = false;

    public Tunnel(Coordinate location, TunnelType type, String edgeColor, Direction direction) {
        this.location = location;
        this.type = type;
        this.edgeColor = edgeColor;
        this.outDirection = direction;
    }

    public Coordinate getLocation() {
        return location;
    }

    public TunnelType getType() {
        return type;
    }


    public String getEdgeColor() {
        return edgeColor;
    }

    public Direction getOutDirection() {
        return outDirection;
    }

    public void translate(int xDiff, int yDiff) {
        location = location.translate(xDiff, yDiff);
    }

    public void setType(TunnelType newType) {
        this.type = newType;
    }

    public void markAsStart() {
        startNode = true;
    }

    public boolean isStartNode() {
        return startNode;
    }
}
