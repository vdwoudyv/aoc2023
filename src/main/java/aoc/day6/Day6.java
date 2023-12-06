package aoc.day6;

import aoc.Day;
import aoc.util.AocTools;

import java.util.List;
import java.util.stream.Stream;

public class Day6 implements Day {
    @Override
    public String runPartOne(boolean testInput) {
        List<String> input = AocTools.read(testInput, this);
        long[] times = Stream.of(input.get(0).split(":")[1].trim().split(" "))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .mapToLong(Long::parseLong)
                .toArray();
        long[] records = Stream.of(input.get(1).split(":")[1].trim().split(" "))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .mapToLong(Long::parseLong)
                .toArray();
        long result = 1;
        for (int i = 0; i < times.length;i++) {
            result *= getNumberOfSolutions(records[i], times[i]);
        }
        return "" + result;
    }

    @Override
    public String runPartTwo(boolean testInput) {
        List<String> input = AocTools.read(testInput, this);
        long time = Long.parseLong(input.get(0).split(":")[1].trim().replaceAll(" ", ""));
        long record = Long.parseLong(input.get(1).split(":")[1].trim().replaceAll(" ", ""));
        return "" + getNumberOfSolutions(record, time);
    }



    public Long getNumberOfSolutions(Long currentRecord, Long time) {
        // We need all integer solutions between the two solutions of following equation:
        // x * (time-x) - record = 0
        // x^2 - time*x + record = 0
        // (-b +- sqrt(b^2 - 4ac)) / 2a
        // a = 1, b = -time, c = record
        double discriminant = Math.sqrt(time*time - 4*currentRecord);
        double first = (time + discriminant) / 2.0;
        double second = (time - discriminant) / 2.0;
        long secondResult = (long) Math.floor(Math.max(first, second));
        long firstResult = (long) Math.ceil(Math.min(first, second));

        long numberOfSolutions = secondResult - firstResult + 1L;
        if (firstResult * secondResult == currentRecord) {
            numberOfSolutions -= 2;
        }
        return numberOfSolutions;
    }
}
