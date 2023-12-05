package aoc.day3;

public class Symbol extends Item {
    private final String symbol;

    public Symbol(int row, int beginColumn, int endColumn, String symbol) {
        super(row, beginColumn, endColumn);
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
