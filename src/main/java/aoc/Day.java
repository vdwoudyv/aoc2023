package aoc;

public interface Day {

    default boolean forceTest() {
        return false;
    }

    String runPartOne(boolean testInput);

    String runPartTwo(boolean testInput);

    default boolean mustPrint() {
        return false;
    }
}
