package aoc.day5;

public class MapElement {

    private long sourceStart;
    private long destStart;
    private long length;


    public MapElement(long sourceStart, long destStart, long length) {
        this.sourceStart = sourceStart;
        this.destStart = destStart;
        this.length = length;
    }

    public boolean matches(long input) {
        return input >= sourceStart && input < sourceStart + length;
    }

    public long map(long input) {
        if (matches(input)) {
            return (input - sourceStart + destStart);
        } else {
            throw new IllegalArgumentException("Outside of my range");
        }
    }
}
