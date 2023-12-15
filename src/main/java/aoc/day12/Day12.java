package aoc.day12;

import aoc.Day;
import aoc.util.AocTools;
import aoc.util.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day12 implements Day {

    @Override
    public String runPartOne(boolean testInput) {
        List<Long> arrangements = AocTools.read(testInput, this).stream().map(
                        s -> {
                            String[] parts = s.split("\\s");
                            List<Integer> numbers = Stream.of(parts[1].split(",")).map(
                                    Integer::parseInt).collect(Collectors.toList()
                            );
                            return new Tuple<>(parts[0], numbers);
                        }
                ).map(t -> new SpringConfiguration().getNumberOfMatches(t.first(), t.second()))
                .toList();

        return "" + arrangements.stream().mapToLong(Long::longValue).sum();
    }

    @Override
    public String runPartTwo(boolean testInput) {
        List<Long> arrangements = AocTools.read(testInput, this).stream().map(
                        s -> {
                            String[] parts = s.split("\\s");
                            List<Integer> numbers = Stream.of(parts[1].split(",")).map(
                                    Integer::parseInt).toList();
                            String pairs = parts[0] + "?" + parts[0] + "?" + parts[0] + "?" + parts[0] + "?" + parts[0];
                            List<Integer> allNumbers = new ArrayList<>();
                            allNumbers.addAll(numbers);
                            allNumbers.addAll(numbers);
                            allNumbers.addAll(numbers);
                            allNumbers.addAll(numbers);
                            allNumbers.addAll(numbers);
                            return new Tuple<>(pairs, allNumbers);
                        }
                ).map(t -> new SpringConfiguration().getNumberOfMatches(t.first(), t.second()))
                .toList();

        return "" + arrangements.stream().mapToLong(Long::longValue).sum();

        }
}
