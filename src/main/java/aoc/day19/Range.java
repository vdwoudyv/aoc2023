package aoc.day19;

public record Range(long min, long max) {

    public boolean isPossible() {
        return min <= max;
    }

    public long nbPossibilities() {
        return isPossible() ? max - min + 1 : 0;
    }
}
