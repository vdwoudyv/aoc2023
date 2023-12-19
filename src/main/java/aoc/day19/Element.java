package aoc.day19;

public record Element(long x, long m, long a, long s) {

    public long rating() {
        return x + m + a + s;
    }
}
