package aoc.day17;

import aoc.Day;
import aoc.util.AocTools;
import aoc.util.Coordinate;
import aoc.util.Direction;
import aoc.util.routing.Edge;
import aoc.util.routing.Node;
import aoc.util.routing.World;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day17 implements Day {

    @Override
    public boolean forceTest() {
        return false;
    }

    /**
     * Takes around 22 seconds on my laptop
     */
    @Override
    public String runPartOne(boolean testInput) {
        long startTime = System.currentTimeMillis();
        List<String> input = AocTools.read(testInput, this);
        World<CityBlock> world = constructWorldCrucibles(input);
        long lowestLoss = getLowestLoss(input, world);
        System.out.println("Time: " + (System.currentTimeMillis() - startTime) + "ms");
        return "" + lowestLoss;
    }

    /**
     * Takes around 25 minutes on my laptop (each route takes about 35 seconds, but to be sure, we need to calculate
     * 40 routes: starting east and south, and arriving east/south (with steps 1-10).
     */
    @Override
    public String runPartTwo(boolean testInput) {
        long startTime = System.currentTimeMillis();
        List<String> input = AocTools.read(testInput, this);
        World<CityBlock> world = constructWorldUltraCrucibles(input);
        long lowestLoss = getLowestLoss(input, world);
        System.out.println("Time: " + (System.currentTimeMillis() - startTime) + "ms");
        return "" + lowestLoss;

    }


    private static long getLowestLoss(List<String> input, World<CityBlock> world) {
        Coordinate end = new Coordinate(input.get(0).length()-1, input.size()-1);
        Coordinate start = new Coordinate(0,0);
        List<CityBlock> startNodes = world.getNodes().stream()
                .filter(n -> n.getCoordinate().equals(start))
                .filter(n -> n.getInDirection() == Direction.SOUTH || n.getInDirection() == Direction.EAST)
                .filter(n -> n.getSteps() == 1)
                .toList();
        List<CityBlock> endNodes = world.getNodes().stream()
                .filter(n -> n.getCoordinate().equals(end))
                .filter(n -> n.getInDirection() == Direction.SOUTH || n.getInDirection() == Direction.EAST)
                .toList();
        long lowestLoss = Long.MAX_VALUE;
        for (CityBlock s: startNodes) {
            for (CityBlock e: endNodes) {
                List<Edge> route = world.getRouteDs(s, e);
                long loss = route.stream().mapToLong(Edge::getWeight).sum();
                System.out.println("Route from " + s + " to " + e + " has loss " + loss);
                world.reset();
                lowestLoss = Math.min(lowestLoss, loss);
            }
        }

        return lowestLoss;
    }




    /**
     * Construct a world from the input.
     * Each character from the input does not correspond to a single, but to multiple nodes.
     * For each coordinate, we will create a node for each direction you could have entered this coordinate from and
     * each number of steps (1, 2 or 3) that could have been taken to get there.
     * For example, ([1,1], South, 1) is one possible node. ([1,1], South, 2) is another possible node.
     * For the edges: You can enter a node from each neighbour if the number of steps of the destination node is 1. Else
     * you can only enter from a neighbour with the same direction and one step lower.
     *
     * <br>
     * A few Examples:
     * <ul>
     * <li>For example, suppose you have following node: ([1,1], South,2):
     * <ul>
     *     <li>You have only one incoming edge from ([1,0], South, 1)</li>
     *     <li>You have following outgoing edges: ([0,1], West, 1), ([2,1], East, 1), ([1,2], South, 3)
     * </ul>
     * Each node has exactly three outgoing edges. The number of incoming edges is one for each node with steps 2 and 3
     * but high for the nodes with step 1 since every neighbour with either step 1, 2 or 3 can go there.
     *
     * @param input the input strings
     * @return a world constructed to the rules above
     */
    private World constructWorldCrucibles(List<String> input) {
        Map<String, CityBlock> allCityblocks = new HashMap<>();
        int worldHeight = input.size();
        int worldWidth = input.get(0).length();
        for (int y = 0; y < worldHeight; y++) {
            for (int x = 0; x < worldWidth; x++) {
                int heatLoss = Integer.parseInt("" + input.get(y).charAt(x));
                for (int steps = 1; steps <= 3; steps++) {
                    for (Direction d : Direction.values()) {
                        Coordinate location = new Coordinate(x, y);
                        CityBlock cb = new CityBlock(location, heatLoss, d, steps);
                        String lookup = location + "-" + d + "-" + steps;
                        allCityblocks.put(lookup, cb);
                    }
                }
            }
        }
        for (CityBlock cb : allCityblocks.values()) {
            // Outgoing edges are easiest. We have one for each neighbour except in the opposite direction where we
            // come from
            Arrays.stream(Direction.values()).filter(d -> d != cb.getInDirection().getOpposite()).forEach(d -> {
                int steps = d == cb.getInDirection() ? cb.getSteps() + 1 : 1;
                Coordinate target = cb.getCoordinate().getAdjacent(d);
                if (target.isInBounds(worldWidth, worldHeight) && steps < 4) {
                    // coordinate + "-" + inDirection + "-" + steps + "-" + heatLoss;
                    String lookup = target + "-" + d + "-" + steps;
                    CityBlock neighbour = allCityblocks.get(lookup);
                    if (neighbour != null) {
                        Edge e = new Edge(neighbour.getHeatLoss(), cb, neighbour);
                        cb.addEdge(e);
                        neighbour.addEdge(e);
                    } else {
                        System.out.println("This should not happen!");
                    }
                }
            });
        }
        // All cityblocks without an incoming edge could be removed from the graph, but this optimization probably
        // won't be important as those nodes are not considered in the routing algorithm anyway.
        return new World<>(allCityblocks.values().stream().toList());
    }



    private World<CityBlock> constructWorldUltraCrucibles(List<String> input) {
        Map<String, CityBlock> allCityblocks = new HashMap<>();
        int worldHeight = input.size();
        int worldWidth = input.get(0).length();
        for (int y = 0; y < worldHeight; y++) {
            for (int x = 0; x < worldWidth; x++) {
                int heatLoss = Integer.parseInt("" + input.get(y).charAt(x));
                for (int steps = 1; steps <= 10; steps++) {
                    for (Direction d : Direction.values()) {
                        Coordinate location = new Coordinate(x, y);
                        CityBlock cb = new CityBlock(location, heatLoss, d, steps);
                        String lookup = location + "-" + d + "-" + steps;
                        allCityblocks.put(lookup, cb);
                    }
                }
            }
        }


        for (CityBlock cb : allCityblocks.values()) {
            // All nodes that have steps < 4 have a single outgoing edge, namely in the direction they come from
            // to a single step higher.
            if (cb.getSteps() < 4) {
                Direction d = cb.getInDirection();
                int steps = cb.getSteps() + 1;
                Coordinate target = cb.getCoordinate().getAdjacent(d);
                if (target.isInBounds(worldWidth, worldHeight)) {
                    // coordinate + "-" + inDirection + "-" + steps + "-" + heatLoss;
                    String lookup = target + "-" + d + "-" + steps;
                    CityBlock neighbour = allCityblocks.get(lookup);
                    if (neighbour != null) {
                        Edge e = new Edge(neighbour.getHeatLoss(), cb, neighbour);
                        cb.addEdge(e);
                        neighbour.addEdge(e);
                    } else {
                        System.out.println("This should not happen!");
                    }
                }
            } else {
                // When nodes have 4 or more steps, they can go in any direction except the opposite where they came
                // from unless they are 10 steps far.

                Arrays.stream(Direction.values()).filter(d -> d != cb.getInDirection().getOpposite()).forEach(d -> {
                    int steps = d == cb.getInDirection() ? cb.getSteps() + 1 : 1;
                    Coordinate target = cb.getCoordinate().getAdjacent(d);
                    if (target.isInBounds(worldWidth, worldHeight) && steps < 11) {
                        // coordinate + "-" + inDirection + "-" + steps + "-" + heatLoss;
                        String lookup = target + "-" + d + "-" + steps;
                        CityBlock neighbour = allCityblocks.get(lookup);
                        if (neighbour != null) {
                            Edge e = new Edge(neighbour.getHeatLoss(), cb, neighbour);
                            cb.addEdge(e);
                            neighbour.addEdge(e);
                        } else {
                            System.out.println("This should not happen!");
                        }
                    }
                });
            }
        }
        // All cityblocks without an incoming edge could be removed from the graph, but this optimization probably
        // won't be important as those nodes are not considered in the routing algorithm anyway.
        return new World<>(allCityblocks.values().stream().toList());
    }


}
