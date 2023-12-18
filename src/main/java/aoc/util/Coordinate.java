package aoc.util;

public record Coordinate(int x, int y) {

    public Coordinate translate(int diffX, int diffY) {
        return new Coordinate(x + diffX, y + diffY);
    }

    public int getManhattan(Coordinate other) {
        return Math.abs(this.x() - other.x()) + Math.abs(this.y() - other.y());
    }

    public boolean touches(Coordinate other) {
        return Math.abs(this.x() - other.x()) <= 1 && Math.abs(this.y() - other.y()) <= 1;
    }

    public Coordinate getAdjacent(Direction direction) {
        return translate(direction.getDiffX(), direction.getDiffY());
    }
    
    public boolean sameRow(Coordinate other) {
        return y() == other.y();
    }

    public boolean sameColumn(Coordinate other) {
        return x() == other.x();
    }

    /**
     * Returns whether the other coordinate is to the south, north, east or west of this coordinate where
     * the x axis increases to the east, and the y axis increases to the south.
     */
    public Direction compare(Coordinate other) {
        if (sameRow(other)) {
            return x() < other.x() ? Direction.EAST : Direction.WEST;
        } else if (sameColumn(other)) {
            return y() < other.y() ? Direction.SOUTH : Direction.NORTH;
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }

    public boolean isInBounds(int worldWidth, int worldHeight) {
        if (x < 0 || x >= worldWidth || y < 0 || y >= worldHeight) {
            return false;
        } else {
            return true;
        }
    }
}
