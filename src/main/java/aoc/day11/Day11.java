package aoc.day11;

import aoc.Day;
import aoc.util.AocTools;
import aoc.util.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class Day11 implements Day {
    @Override
    public String runPartOne(boolean testInput) {
        return run(testInput, 2);
    }

    @Override
    public String runPartTwo(boolean testInput) {
        return run(testInput, 1000000);
    }

    private String run(boolean testInput, int shiftSize) {
        List<List<String>> input = AocTools.parseAsArray(AocTools.read(testInput, this), s -> s);
        List<Coordinate> galaxies = parseGalaxies(input, shiftSize);
        long total = 0;
        for (int i = 0; i < galaxies.size(); i++) {
            Coordinate current = galaxies.get(i);
            for (int j = i + 1; j < galaxies.size(); j++) {
                Coordinate other = galaxies.get(j);
                total += current.getManhattan(other);
            }
        }
        return "" + total;
    }

    private List<Coordinate> parseGalaxies(List<List<String>> input, int shiftSize) {
        List<Integer> emptyRowIndices = new ArrayList<>();
        List<Integer> emptyColIndices = new ArrayList<>();
        List<Coordinate> galaxies = new ArrayList<>();
        // Get galaxy coordinates and empty rows
        for (int y = 0; y < input.size(); y++) {
            List<String> row = input.get(y);
            List<Coordinate> forRow = new ArrayList<>();
            for (int x = 0; x < row.size(); x++) {
                if (row.get(x).equals("#")) {
                    forRow.add(new Coordinate(x, y));
                }
            }
            if (forRow.isEmpty()) {
                emptyRowIndices.add(y);
            } else {
                galaxies.addAll(forRow);
                forRow.clear();
            }
        }
        // Get empty columns
        for (int i = 0; i < input.get(0).size(); i++) {
            final int index = i;
            if (galaxies.stream().noneMatch(c -> c.x() == index)) {
                emptyColIndices.add(index);
            }
        }
        // Shift all galaxies
        return galaxies.stream().map(c ->
                c.translate((int) emptyColIndices.stream().filter(i -> i < c.x()).count() * (shiftSize - 1),
                        (int) emptyRowIndices.stream().filter(i -> i < c.y()).count() * (shiftSize - 1)
                )
        ).toList();
    }
}
