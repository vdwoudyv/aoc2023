package aoc;

public interface Day {

    String runPartOne(boolean testInput);

    String runPartTwo(boolean testInput);

    default boolean forceTest() {
        return false;
    }
}
