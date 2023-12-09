package aoc.day7;

import aoc.Day;
import aoc.util.AocTools;

import java.util.List;

public class Day7 implements Day {


    @Override
    public String runPartOne(boolean testInput) {
        List<Hand> hands = AocTools.read(testInput, this).stream()
                .map(s -> new Hand(s, false))
                .sorted()
                .toList();
        long result = 0;
        for (int i = 0; i < hands.size(); i++) {
            result += hands.get(i).getBid() * (i+1);
        }
        return "" + result;
    }

    @Override
    public String runPartTwo(boolean testInput) {
        List<Hand> hands = AocTools.read(testInput, this).stream()
                .map(s -> new Hand(s, true))
                .sorted()
                .toList();

        long result = 0;
        for (int i = 0; i < hands.size(); i++) {
            result += hands.get(i).getBid() * (i+1);
        }
        return "" + result;
    }
}
