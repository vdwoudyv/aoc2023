package aoc.day18;

import aoc.util.Coordinate;
import aoc.util.Direction;
import aoc.util.Tuple;

import java.util.*;

public class Field {

    private Map<Coordinate, Tunnel> tunnels = new HashMap<>();
    private Tuple<Integer, Integer> dimension;
    private boolean normalized = true;

    public Field(List<String> input) {
        Coordinate current = new Coordinate(0, 0);
        for (int y = 0; y < input.size(); y++) {
            String line = input.get(y);
            String[] parts = line.split(" ");
            Direction direction = switch (parts[0]) {
                case "R" -> Direction.EAST;
                case "L" -> Direction.WEST;
                case "D" -> Direction.SOUTH;
                case "U" -> Direction.NORTH;
                default -> throw new IllegalArgumentException("Unknown direction: " + parts[0]);
            };
            int distance = Integer.parseInt(parts[1]);
            String color = parts[2];
            current = addTunnels(direction, distance, color, current);
        }
        normalizeCoordinates();
    }

    private Coordinate addTunnels(Direction direction, int distance, String color, Coordinate start) {
        Coordinate current = null;
        for (int i = 0; i <= distance; i++) {
            current = (i == 0) ? start : current.getAdjacent(direction);
            TunnelType t = switch (direction) {
                case NORTH, SOUTH -> TunnelType.VERTICAL;
                case EAST, WEST -> TunnelType.HORIZONTAL;
            };

            Optional<Tunnel> existing = getTunnelForCoordinate(current);
            if (existing.isPresent()) {
                Tunnel tunnel = existing.get();
                TunnelType newType = i == 0 ? TunnelType.fromTo(tunnel.getOutDirection(), direction) : TunnelType.fromTo(direction, tunnel.getOutDirection());
                tunnel.setType(newType);
            } else {
                addTunnel(new Tunnel(current, t, color, direction));
            }
        }
        return current;
    }

    public void addTunnel(Tunnel tunnel) {
        if (tunnels.isEmpty()) {
            tunnel.markAsStart();
        }
        tunnels.put(tunnel.getLocation(), tunnel);
        normalized = false;
    }

    public void normalizeCoordinates() {
        if (normalized) {
            return;
        }
        Map<Coordinate, Tunnel> newMap = new HashMap<>();
        List<Coordinate> locations = tunnels.values().stream().map(Tunnel::getLocation).toList();
        int minX = locations.stream().mapToInt(Coordinate::x).min().orElse(0);
        int minY = locations.stream().mapToInt(Coordinate::y).min().orElse(0);
        int maxX = locations.stream().mapToInt(Coordinate::x).max().orElse(0);
        int maxY = locations.stream().mapToInt(Coordinate::y).max().orElse(0);
        for (Tunnel t : tunnels.values()) {
              t.translate(-minX, -minY);
              newMap.put(t.getLocation(), t);
            }
        dimension = new Tuple<>(maxX - minX, maxY - minY);
        tunnels = newMap;
        normalized = true;
    }

    public Tuple<Integer, Integer> getDimensions() {
        normalizeCoordinates();
        return dimension;
    }

    public List<Coordinate> getEnclosedPoints() {
        List<List<Boolean>> verticalIntersections = new ArrayList<>();
        Tuple<Integer, Integer> dimensions = getDimensions();
        for (int y = 0; y < dimensions.second(); y++) {
            List<Boolean> row = new ArrayList<>();
            for (int x = 0; x < dimensions.first(); x++) {
                Coordinate current = new Coordinate(x, y);
                Optional<Tunnel> relevantTunnel = getTunnelForCoordinate(current);
                if (relevantTunnel.isPresent()) {
                    row.add(relevantTunnel.get().getType().intersectsVertically());
                } else {
                    row.add(false);
                }
            }
            verticalIntersections.add(row);
        }
        List<Coordinate> result = new ArrayList<>();
        for (int y = 0; y < dimensions.second(); y++) {
            for (int x = 0; x < dimensions.first(); x++) {
                Coordinate current = new Coordinate(x, y);
                if (getTunnelForCoordinate(current).isEmpty()) {
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


    private Optional<Tunnel> getTunnelForCoordinate(Coordinate c) {
        return Optional.ofNullable(tunnels.get(c));
    }


    public int getCapacity() {
        return tunnels.size() + getEnclosedPoints().size();
    }



    public String toString() {
        normalizeCoordinates();

        String[][] field = new String[dimension.second() + 1][dimension.first() + 1];
        for (Tunnel t : tunnels.values()) {
            field[t.getLocation().y()][t.getLocation().x()] =  t.getType().getSymbol();
        }
        for (Coordinate c : getEnclosedPoints()) {
            field[c.y()][c.x()] = "X";
        }
        StringBuilder sb = new StringBuilder();
        for (String[] row : field) {
            sb.append("\n");
            for (String column : row) {
                sb.append(column == null ? "." : column);
            }
        }
        return sb.toString();
    }
}
