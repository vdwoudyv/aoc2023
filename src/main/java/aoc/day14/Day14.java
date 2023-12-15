package aoc.day14;

import aoc.Day;
import aoc.util.AocTools;

import java.util.ArrayList;
import java.util.List;

public class Day14 implements Day {


    @Override
    public boolean forceTest() {
        return false;
    }

    @Override
    public String runPartOne(boolean testInput) {
        Lever input = new Lever(AocTools.read(testInput, this));
        input.tiltNorth();
        return "" + input.totalLoad();
    }

    @Override
    public String runPartTwo(boolean testInput) {
        Lever input = new Lever(AocTools.read(testInput, this));
        // We kind of assume that there will be repetition after a while.
        for (int i = 0; i < 500; i++) {
            input.tiltNorth();
            input.tiltWest();
            input.tiltSouth();
            input.tiltEast();
        }
        List<Integer> sequence = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            input.tiltNorth();
            input.tiltWest();
            input.tiltSouth();
            input.tiltEast();
            sequence.add(input.totalLoad());
        }
        List<Integer> subsequence = getSubsequence(sequence);
        int correctIndex = ((sequence.size() - 1) + ((1000000000 - 1000) % subsequence.size())) % sequence.size();
        return sequence.get(correctIndex) + "";
    }

    private List<Integer> getSubsequence(List<Integer> sequence) {
        List<Integer> subsequence = new ArrayList<>();
        for (int i = 0; i < sequence.size(); i++) {
            if (!subsequence.isEmpty() && sequence.get(i).equals(subsequence.get(0))) {
                boolean valid = true;
                for (int j = 0; j < subsequence.size(); j++) {
                    if (!subsequence.get(j).equals(sequence.get(i + j))) {
                        valid = false;
                        break;
                    }
                }
                if (valid) {
                    return subsequence;
                } else {
                    subsequence.add(sequence.get(i));
                }
            } else {
                subsequence.add(sequence.get(i));
            }
        }
        return null;
    }
}
