package aoc.day2;

import java.util.Map;

public record Draw(Map<String, Integer> colorMap) {

    public boolean isPossible(Map<String, Integer> numberOfStones) {
        for (Map.Entry<String, Integer> entry : colorMap.entrySet()) {
            if (numberOfStones.get(entry.getKey()) < entry.getValue()) {
                return false;
            }
        }
        return true;
    }
}
