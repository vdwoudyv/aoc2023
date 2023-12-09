package aoc.day8;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SimpleMap {

    private Map<String, String> leftTurns = new HashMap<>();
    private Map<String, String> rightTurns = new HashMap<>();

    public void addDirection(String directionLine) {
        String from = directionLine.substring(0,3);
        String left = directionLine.substring(7,10);
        String right = directionLine.substring(12,15);
        leftTurns.put(from, left);
        rightTurns.put(from, right);
    }

    public long numberOfSteps(String direction) {
        long steps = 0L;
        int currentDirectionIndex = 0;
        byte[] bytes = direction.getBytes();
        String currentLocation = "AAA";
        while (! currentLocation.equals("ZZZ")) {
            if (currentDirectionIndex == bytes.length) {
                currentDirectionIndex = 0;
            }
            if (bytes[currentDirectionIndex] == 'L') {
                currentLocation = leftTurns.get(currentLocation);
            } else {
                currentLocation = rightTurns.get(currentLocation);
            }
            steps++;
            currentDirectionIndex++;
        }
        return steps;
    }

    public BigDecimal numberOfStepsForGhosts(String direction) {
        List<String> currentLocations = new ArrayList<>(leftTurns.keySet().stream().filter(s->s.endsWith("A")).toList());
        List<BigDecimal> times = currentLocations.stream().map(s->getCycleTime(s, direction)).map(l->new BigDecimal("" + l)).toList();
        return leastCommonMultiple(times);
    }

    private BigDecimal leastCommonMultiple(List<BigDecimal> values) {
        BigDecimal acc = leastCommonMultiple(values.get(0), values.get(1));
        for (int i = 2; i < values.size(); i++) {
            acc = leastCommonMultiple(acc, values.get(i));
        }
        return acc;
    }

    private BigDecimal leastCommonMultiple(BigDecimal first, BigDecimal second) {
        if (first == BigDecimal.ZERO || second == BigDecimal.ZERO) {
            return BigDecimal.ZERO;
        }
        BigDecimal highest = first.compareTo(second) <= 0 ? second : first;
        BigDecimal lowest = first.compareTo(second) <= 0 ? first : second;
        BigDecimal result = highest;
        while (result.remainder(lowest).compareTo(BigDecimal.ZERO) != 0) {
            result = result.add(highest);
        }
        return result;
    }

    public long getCycleTime(String start, String direction) {
        long steps = 0L;
        int currentDirectionIndex = 0;
        byte[] bytes = direction.getBytes();
        String currentLocation = start;
        while (! currentLocation.endsWith("Z") || (steps == 0)) {
            if (currentDirectionIndex >= bytes.length) {
                currentDirectionIndex = currentDirectionIndex % bytes.length;
            }
            if (bytes[currentDirectionIndex] == 'L') {
                currentLocation = leftTurns.get(currentLocation);
            } else {
                currentLocation = rightTurns.get(currentLocation);
            }
            steps++;
            currentDirectionIndex++;
        }
        return steps;
    }

}
