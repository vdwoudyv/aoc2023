package aoc.day15;

public class Lens {
    private final String label;
    private int focalLength;

    public Lens(String label, int focalLength) {
        this.label = label;
        this.focalLength = focalLength;
    }

    public String label() {
        return label;
    }

    public int getFocalLength() {
        return focalLength;
    }

    public void setFocalLength(int focalLength) {
        this.focalLength = focalLength;
    }
}
