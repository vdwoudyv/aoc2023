package aoc.day7;

public enum HandType {

    FIVE_OF_A_KIND(6),
    FOUR_OF_A_KIND(5),
    FULL_HOUSE(4),
    THREE_OF_A_KIND(3),
    TWO_PAIRS(2),
    ONE_PAIR(1),
    HIGH_CARD(0);

    private int handValue;

    private HandType(int value) {
        this.handValue = value;
    }

    public int getHandValue() {
        return handValue;
    }
}
