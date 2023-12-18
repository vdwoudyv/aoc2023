package aoc.day17;

import aoc.util.Coordinate;
import aoc.util.Direction;
import aoc.util.routing.Node;

public class CityBlock extends Node {

    private final Coordinate coordinate;
    private final int heatLoss;
    private final Direction inDirection;
    private final int steps;

    public CityBlock(Coordinate coordinate, int heatLoss, Direction inDirection, int steps) {
        this.coordinate = coordinate;
        this.heatLoss = heatLoss;
        this.inDirection = inDirection;
        this.steps = steps;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public int getHeatLoss() {
        return heatLoss;
    }

    public Direction getInDirection() {
        return inDirection;
    }

    public int getSteps() {
        return steps;
    }

    @Override
    public String toString() {
        return coordinate + "-" + inDirection + "-" + steps + "-" + heatLoss;
    }
}
