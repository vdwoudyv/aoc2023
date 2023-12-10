package aoc.day10;

import aoc.util.Coordinate;
import aoc.util.Direction;

import java.util.ArrayList;
import java.util.List;

public class Traversal {

    public List<List<Pipe>> network = new ArrayList<>();
    private List<Coordinate> coordinates = new ArrayList<>();

    public Traversal(List<List<Pipe>> network) {
        this.network = network;
        traverse();

    }

    public List<Coordinate> getRoute() {
        return coordinates;
    }

    private void traverse() {
        Coordinate start = getStart();
        coordinates.add(start);
        Coordinate currentLocation = null;
        Direction currentDirection = null;
        for (Direction direction: Direction.values()) {
            if (currentLocation == null) {
                Coordinate destination = getNext(start,direction);
                if (destination != null && getPipeAt(destination).connectsTo(direction.getOpposite())) {
                    currentLocation = destination;
                    currentDirection = direction;
                }
            }
        }
        coordinates.add(currentLocation);
        while (!currentLocation.equals(start)) {
            Pipe current = getPipeAt(currentLocation);
            Direction nextDirection = current.nextDirection(currentDirection.getOpposite());
            Coordinate nextLocation = currentLocation.translate(nextDirection.getDiffX(), nextDirection.getDiffY());
            coordinates.add(nextLocation);
            currentLocation = nextLocation;
            currentDirection = nextDirection;
        }
    }

    private Coordinate getStart() {
        for (int y = 0; y < network.size(); y++) {
            for (int x = 0; x < network.get(y).size(); x++) {
                if (network.get(y).get(x) == Pipe.START) {
                    return new Coordinate(x, y);
                }
            }
        }
        return null;
    }

    private Coordinate getNext(Coordinate start, Direction direction) {
        int x = start.x() + direction.getDiffX();
        int y = start.y() + direction.getDiffY();
        if (x < 0 || y < 0 || y >= network.size() || x >= network.get(y).size()) {
            return null;
        }
        return new Coordinate(x, y);
    }

    public Pipe getPipeAt(Coordinate c) {
        return network.get(c.y()).get(c.x());
    }

    public List<Coordinate> getEnclosedPoints() {
        List<List<Boolean>> verticalIntersections = new ArrayList<>();
        for (int y = 0; y < network.size(); y++) {
            List<Boolean> row = new ArrayList<>();
            for (int x = 0; x < network.get(y).size(); x++) {
                Coordinate current = new Coordinate(x, y);
                if (coordinates.contains(current) && getRealPipeAt(current).intersectsVertically()) {
                    row.add(true);
                } else {
                    row.add(false);
                }
            }
            verticalIntersections.add(row);
        }
        List<Coordinate> result = new ArrayList<>();
        for (int y = 0; y < network.size(); y++) {
            for (int x = 0; x < network.get(y).size(); x++) {
                Coordinate current = new Coordinate(x, y);
                if (!coordinates.contains(current)) {
                    int numberOfIntersections = 0;
                    for (int index = 0; index < x; index++) {
                        if (verticalIntersections.get(y).get(index)) {
                            numberOfIntersections++;
                        }
                    }
                    if (numberOfIntersections % 2 == 1) {
                        result.add(current);
                    }
                }
            }
        }
        return result;
    }


    private Pipe getRealPipeAt(Coordinate c) {
        Pipe p = getPipeAt(c);
        if (p == Pipe.START) {
            Coordinate firstNeighbour = coordinates.get(1);
            Coordinate secondNeightbour = coordinates.get(coordinates.size() - 2);
            Pipe realPipe = Pipe.fromDirections(c.compare(firstNeighbour), c.compare(secondNeightbour));
            return realPipe;
        }
        else {
            return p;
        }
    }
}
