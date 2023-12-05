package aoc.day3;

public class Number extends Item {
    private final int numberValue;

    public Number(int row, int beginColumn, int endColumn, int number) {
        super(row, beginColumn, endColumn);
        this.numberValue = number;
    }

    public int getNumberValue() {
        return numberValue;
    }
}
