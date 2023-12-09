package aoc.day8;

import aoc.Day;
import aoc.util.AocTools;

import java.util.List;

public class Day8 implements Day {


    @Override
    public String runPartOne(boolean testInput) {
        SimpleMap map = new SimpleMap();
        List<List<String>> groups = AocTools.getGroups(AocTools.read(testInput ? "TestInput1.txt" : "Input.txt", this));
        groups.get(1).forEach(map::addDirection);
        return "" + map.numberOfSteps(groups.get(0).get(0));
    }

    @Override
    public String runPartTwo(boolean testInput) {
        SimpleMap map = new SimpleMap();
        List<List<String>> groups = AocTools.getGroups(AocTools.read(testInput ? "TestInput2.txt" : "Input.txt", this));
        groups.get(1).forEach(map::addDirection);
        return "" + map.numberOfStepsForGhosts(groups.get(0).get(0));
    }
}
