package aoc.day18;

import aoc.Day;
import aoc.util.AocTools;

public class Day18 implements Day {


    @Override
    public boolean forceTest() {
        return false;
    }


    @Override
    public boolean mustPrint() {
        return true;
    }

    @Override
    public String runPartOne(boolean testInput) {
        Field input = new Field(AocTools.read(testInput, this));
        return "" + input.getCapacity();
    }

    @Override
    public String runPartTwo(boolean testInput) {
        Field input = new Field(AocTools.read(testInput, this));
        return input.toString();
    }
}
