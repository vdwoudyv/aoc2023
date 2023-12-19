package aoc.day18;

import aoc.util.Direction;

public enum TunnelType {

    VERTICAL("|", Direction.NORTH, Direction.SOUTH, true),
    HORIZONTAL("-", Direction.EAST, Direction.WEST, false),
    NORTH_EAST("/", Direction.NORTH, Direction.EAST, false),
    NORTH_WEST("\\", Direction.NORTH, Direction.WEST, false),
    SOUTH_WEST("/", Direction.SOUTH, Direction.WEST, true),
    SOUTH_EAST("\\", Direction.SOUTH, Direction.EAST, true);

    private final Direction direction1;
    private final Direction direction2;
    private final boolean intersectsVertically;
    private final String symbol;

    TunnelType(String symbol, Direction direction1, Direction direction2, boolean intersectsVertically) {
        this.direction1 = direction1;
        this.direction2 = direction2;
        this.intersectsVertically = intersectsVertically;
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public boolean intersectsVertically() {
        return intersectsVertically;
    }

    public static TunnelType fromTo(Direction from, Direction to) {
        return switch (from) {
            case NORTH -> switch (to) {
                case EAST -> NORTH_EAST;
                case WEST -> NORTH_WEST;
                case SOUTH, NORTH -> VERTICAL;
            };
           case EAST -> switch (to) {
               case EAST, WEST -> HORIZONTAL;
               case NORTH -> SOUTH_WEST;
               case SOUTH -> NORTH_WEST;
           };
            case SOUTH -> switch (to) {
                case NORTH, SOUTH -> VERTICAL;
                case EAST -> SOUTH_EAST;
                case WEST -> SOUTH_WEST;
            };
            case WEST -> switch (to) {
                case EAST, WEST -> HORIZONTAL;
                case NORTH -> SOUTH_EAST;
                case SOUTH -> NORTH_EAST;
            };
        };
    }
}
