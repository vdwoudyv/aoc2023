package aoc.day10;

import aoc.util.Direction;

public enum Pipe {

    /*
    | is a vertical pipe connecting north and south.
- is a horizontal pipe connecting east and west.
L is a 90-degree bend connecting north and east.
J is a 90-degree bend connecting north and west.
7 is a 90-degree bend connecting south and west.
F is a 90-degree bend connecting south and east.
     */
    VERTICAL("|", Direction.NORTH, Direction.SOUTH, true),
    HORIZONTAL("-", Direction.EAST, Direction.WEST, false),
    NORTH_EAST("L", Direction.NORTH, Direction.EAST, false),
    NORTH_WEST("J", Direction.NORTH, Direction.WEST, false),
    SOUTH_WEST("7", Direction.SOUTH, Direction.WEST, true),
    SOUTH_EAST("F", Direction.SOUTH, Direction.EAST, true),
    DOT(".",null, null, false),
    START("S", null, null, false);

    private final String symbol;
    private final Direction direction1;
    private final Direction direction2;
    private final boolean intersectsVertically;

    Pipe(String symbol, Direction direction1, Direction direction2, boolean intersectsVertically) {
        this.symbol = symbol;
        this.direction1 = direction1;
        this.direction2 = direction2;
        this.intersectsVertically = intersectsVertically;
    }

    public Direction nextDirection(Direction direction) {
        if (direction == direction1) {
            return direction2;
        } else if (direction == direction2) {
            return direction1;
        } else {
            throw new IllegalArgumentException("Direction " + direction + " is not valid for pipe " + this);
        }
    }

    public boolean connectsTo(Direction direction) {
        return direction == direction1 || direction == direction2;
    }

    public static Pipe fromSymbol(String symbol) {
        for (Pipe pipe: Pipe.values()) {
            if (pipe.symbol.equals(symbol)) {
                return pipe;
            }
        }
        throw new IllegalArgumentException("Symbol " + symbol + " is not a valid pipe");
    }

    public static Pipe fromDirections(Direction one, Direction two) {
        for (Pipe pipe: Pipe.values()) {
            if ((pipe.direction1 == one && pipe.direction2 == two) || (pipe.direction1 == two && pipe.direction2 == one)) {
                return pipe;
            }
        }
        throw new IllegalArgumentException("Directions " + one + " and " + two + " are not a valid pipe");
    }

    public boolean intersectsVertically() {
        return intersectsVertically;
    }
}
