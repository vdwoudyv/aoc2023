package aoc.day19;

import aoc.Day;
import aoc.util.AocTools;

import java.util.List;

public class Day19 implements Day {
    @Override
    public String runPartOne(boolean testInput) {
        List<List<String>> groups = AocTools.getGroups(AocTools.read(testInput, this));
        ElementWorld world = new ElementWorld(groups.get(0));
        List<Element> elements = groups.get(1).stream().map(this::parseElement).toList();
        world.processElements(elements);
        return "" + world.getAcceptedElements().stream().mapToLong(Element::rating).sum();
    }

    @Override
    public String runPartTwo(boolean testInput) {
        List<List<String>> groups = AocTools.getGroups(AocTools.read(testInput, this));
        return "" + new ElementWorld(groups.get(0)).getAllPathsToAcceptance().stream().mapToLong(Path::nbPossibilities).sum();
    }

    @Override
    public boolean forceTest() {
        return false;
    }


    private Element parseElement(String line) {
        String[] split = line.substring(1, line.length()-1).split(",");
        long x = Long.parseLong(split[0].substring(2));
        long m = Long.parseLong(split[1].substring(2));
        long a = Long.parseLong(split[2].substring(2));
        long s = Long.parseLong(split[3].substring(2));
        return new Element(x, m, a, s);
    }
}
