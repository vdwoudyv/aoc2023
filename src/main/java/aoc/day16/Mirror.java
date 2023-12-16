package aoc.day16;

import aoc.util.Direction;

import java.util.List;

public enum Mirror {


    VERTICAL('|') {
        @Override
        List<Direction> targetDirections(Direction incoming) {
            if (incoming == Direction.EAST || incoming == Direction.WEST) {
                return List.of(Direction.NORTH, Direction.SOUTH);
            } else {
                return List.of(incoming);
            }
        }
    },
    HORIZONTAL('-') {
        @Override
        List<Direction> targetDirections(Direction incoming) {
            if (incoming == Direction.NORTH || incoming == Direction.SOUTH) {
                return List.of(Direction.EAST, Direction.WEST);
            } else {
                return List.of(incoming);
            }
        }
    },
    DIAGONAL_UPRIGHT('/') {
        @Override
        List<Direction> targetDirections(Direction incoming) {
            return switch (incoming) {
                case NORTH -> List.of(Direction.EAST);
                case EAST -> List.of(Direction.NORTH);
                case SOUTH -> List.of(Direction.WEST);
                case WEST -> List.of(Direction.SOUTH);
            };
        }
    },
    DIAGONAL_UPLEFT('\\') {
        @Override
        List<Direction> targetDirections(Direction incoming) {
            return switch (incoming) {
                case NORTH -> List.of(Direction.WEST);
                case EAST -> List.of(Direction.SOUTH);
                case SOUTH -> List.of(Direction.EAST);
                case WEST -> List.of(Direction.NORTH);
            };
        }
    },
    DOT('.') {
        @Override
        List<Direction> targetDirections(Direction incoming) {
            return List.of(incoming);
        }
    };

    private final char symbol;

    Mirror(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    abstract List<Direction> targetDirections(Direction incoming);

    @Override
    public String toString() {
        return "" + symbol;
    }

    public static Mirror fromValue(char value) {
        for (Mirror mirror: Mirror.values()) {
            if (mirror.symbol == value) {
                return mirror;
            }
        }
        throw new IllegalArgumentException("Value " + value + " is not a valid mirror");
    }
}
