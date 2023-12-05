package aoc.day2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record Game(int id, List<Draw> draws) {

    public boolean isPossible(Map<String, Integer> stones) {
        for (Draw draw : draws) {
            if (!draw.isPossible(stones)) {
                return false;
            }
        }
        return true;
    }

    public int power() {
        Map<String, Integer> unified = new HashMap<>();
        for (Draw d: draws) {
            for (Map.Entry<String, Integer> entry: d.colorMap().entrySet()) {
                unified.put(entry.getKey(), Math.max(unified.getOrDefault(entry.getKey(), 0), entry.getValue()));
            }
        }
        return unified.values().stream().mapToInt(Integer::intValue).reduce(1, (a,b)->a*b);
    }

}
