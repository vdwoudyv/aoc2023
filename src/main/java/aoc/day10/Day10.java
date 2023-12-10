package aoc.day10;

import aoc.Day;
import aoc.util.AocTools;

import java.util.List;

public class Day10 implements Day {
    @Override
    public String runPartOne(boolean testInput) {
        List<List<Pipe>> pipes = AocTools.parseAsArray(AocTools.read(testInput, this), Pipe::fromSymbol);
        Traversal t = new Traversal(pipes);
        return "" + t.getRoute().size()/2;
    }

    @Override
    public String runPartTwo(boolean testInput) {
        List<List<Pipe>> pipes = AocTools.parseAsArray(AocTools.read(testInput ? "TestInput2.txt" : "Input.txt", this), Pipe::fromSymbol);
        Traversal t = new Traversal(pipes);
        return "" + t.getEnclosedPoints().size();

    }
}
