package aoc.day9;

import aoc.Day;
import aoc.util.AocTools;

import java.util.ArrayList;
import java.util.List;

public class Day9 implements Day {
    @Override
    public String runPartOne(boolean testInput) {
        List<List<Long>> input = AocTools.parseAsArray(AocTools.read(testInput, this),
                Long::parseLong,
                s-> List.of(s.split("\\s+")));
        return "" + input.stream().mapToLong(this::predictNext).sum();
    }

    private long predictNext(List<Long> list) {
        List<Long> diffList = getDiffList(list);
        return list.get(list.size()-1) + diffList.get(diffList.size()-1);
    }

    private long predictPrevious(List<Long> list) {
        List<Long> diffList = getDiffList(list);
        return list.get(0) - diffList.get(0);
    }

    private List<Long> getDiffList(List<Long> list) {
        ArrayList<Long> diffList = new ArrayList<>();
        boolean allZero = true;
        for (int i = 0; i < list.size()-1; i++) {
            long value = list.get(i+1) - list.get(i);
            allZero = allZero && value == 0;
            diffList.add(value);
        }
        if (allZero) {
            diffList.add(0, 0L);
            diffList.add(0L);
        } else {
            List<Long> subList = getDiffList(diffList);
            diffList.add(diffList.get(diffList.size()-1) + subList.get(subList.size()-1));
            diffList.add(0, diffList.get(0) - subList.get(0));
        }
        return diffList;
    }

    public String runPartTwo(boolean testInput) {
        List<List<Long>> input = AocTools.parseAsArray(AocTools.read(testInput, this),
                Long::parseLong,
                s-> List.of(s.split("\\s+")));
        return "" + input.stream().mapToLong(this::predictPrevious).sum();
    }
}
