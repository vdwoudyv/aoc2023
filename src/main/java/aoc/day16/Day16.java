package aoc.day16;

import aoc.Day;
import aoc.util.AocTools;
import aoc.util.Coordinate;
import aoc.util.Direction;

import java.util.List;

public class Day16 implements Day {

    @Override
    public boolean forceTest() {
        return false;
    }

    @Override
    public String runPartOne(boolean testInput) {
        Field field = new Field(AocTools.read(testInput, this), new CellTraversal(Direction.EAST, new Coordinate(0, 1)));
        return "" + field.getNumberOfEnergizedCells();
    }

    @Override
    public String runPartTwo(boolean testInput) {
        // x0 en y van 1 tot testInput length incl direction EAST
        // x[testinput[0].length]  en y van 1 tot testInput length incl direction WEST
        List<String> inputfile = AocTools.read(testInput, this);
        long bestSolution = 0;
        for (int y = 1; y <= inputfile.size(); y++) {
            Field first = new Field(inputfile, new CellTraversal(Direction.EAST, new Coordinate(0, y)));
            Field second = new Field(inputfile, new CellTraversal(Direction.WEST, new Coordinate(inputfile.get(0).length()+1, y)));
            bestSolution = Math.max(bestSolution, first.getNumberOfEnergizedCells());
            bestSolution = Math.max(bestSolution, second.getNumberOfEnergizedCells());
        }
        for (int x = 1; x<= inputfile.get(0).length(); x++) {
            Field first = new Field(inputfile, new CellTraversal(Direction.SOUTH, new Coordinate(x, 0)));
            Field second = new Field(inputfile, new CellTraversal(Direction.NORTH, new Coordinate(x, inputfile.size()+1)));
            bestSolution = Math.max(bestSolution, first.getNumberOfEnergizedCells());
            bestSolution = Math.max(bestSolution, second.getNumberOfEnergizedCells());
        }
        return bestSolution + "";
    }
}
