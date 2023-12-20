package aoc.day18;

import aoc.util.Coordinate;
import aoc.util.Direction;

public class Tunnel {

    private Coordinate startLocation;
    private TunnelType type;
    private Direction outDirection;
    private String edgeColor;
    private int length;

    private boolean startNode = false;

    public Tunnel(Coordinate startLocation, TunnelType type, String edgeColor, Direction direction, int length) {
        this.startLocation = startLocation;
        this.type = type;
        this.edgeColor = edgeColor;
        this.outDirection = direction;
        this.length = length;
    }

    public Coordinate getStartLocation() {
        return startLocation;
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
        startLocation = startLocation.translate(xDiff, yDiff);
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

    public boolean contains(Coordinate c) {
        return getStartLocation().equals(c) ||
                (getType() == TunnelType.HORIZONTAL && overlapsHorizontally(c))
                || (getType() == TunnelType.VERTICAL && overlapsVertically(c));
    }

    private boolean overlapsVertically(Coordinate c) {
        boolean sameColumn = getStartLocation().x() == c.x();
        int yDiff = c.y() - getStartLocation().y();

        return sameColumn &&
                ((yDiff > 0 && yDiff < length && outDirection == Direction.SOUTH)
                        || (yDiff < 0 && -yDiff < length && outDirection == Direction.NORTH));
    }

    private boolean overlapsHorizontally(Coordinate c) {
        boolean sameRow = getStartLocation().y() == c.y();
        int xDiff = c.x() - getStartLocation().x();

        return sameRow &&
                ((xDiff > 0 && xDiff < length && outDirection == Direction.EAST)
                        || (xDiff < 0 && -xDiff < length && outDirection == Direction.WEST));
    }
}
