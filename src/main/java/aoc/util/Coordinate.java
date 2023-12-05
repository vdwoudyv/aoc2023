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

    public boolean sameRow(Coordinate other) {
        return y() == other.y();
    }

    public boolean sameColumn(Coordinate other) {
        return x() == other.x();
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }
}
