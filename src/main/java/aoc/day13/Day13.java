package aoc.day13;

import aoc.Day;
import aoc.util.AocTools;

import java.util.List;
import java.util.stream.Collectors;

public class Day13 implements Day {

    @Override
    public String runPartOne(boolean testInput) {
        return "" +  AocTools.getGroups(AocTools.read(testInput, this))
                .stream()
                .map(TerrainPattern::new)
                .mapToLong(TerrainPattern::getReflectionSummary)
                .sum();
    }

    @Override
    public String runPartTwo(boolean testInput) {
        List<Long> r = AocTools.getGroups(AocTools.read(testInput, this))
                .stream()
                .map(TerrainPattern::new)
                .map(TerrainPattern::getReflectionSummaryWithSmudges)
                .toList();
        return "" + r.stream().mapToLong(c->c).sum();
    }
}
