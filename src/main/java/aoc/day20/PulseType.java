package aoc.day20;

public enum PulseType {

    HIGH("--h-->"),
    LOW("--l-->");

    private String symbol;

    PulseType(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
