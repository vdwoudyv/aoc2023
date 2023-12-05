package aoc.day3;

public class Item {
    private final int row;
    private final int beginColumn;
    private final int endColumn;

    public Item(int row, int beginColumn, int endColumn) {
        this.row = row;
        this.beginColumn = beginColumn;
        this.endColumn = endColumn;
    }

    public boolean isAdjacent(Item other) {
        return (sameRow(other) || adjacentRow(other)) && adjacentColumn(other);

    }

    private boolean adjacentColumn(Item other) {
        return inColumnRange(other.beginColumn) || inColumnRange(other.endColumn);
    }

    private boolean inColumnRange(int number) {
        return number >= this.beginColumn -1 && number <= this.endColumn + 1;
    }

    /**
     * Returns true if the other item is in an adjacent row to this item.
     */
    private boolean adjacentRow(Item other) {
        return Math.abs(this.row - other.row) == 1;
    }

    /**
     * Returns true if the other item is in the same row as this item.
     * @param other the other item
     * @return true if the other item is in the same row as this item
     */
    private boolean sameRow(Item other) {
        return this.row == other.row;
    }
}
