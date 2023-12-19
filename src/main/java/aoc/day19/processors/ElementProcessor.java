package aoc.day19.processors;

import aoc.day19.Element;
import aoc.day19.Range;

import java.util.Map;

public interface ElementProcessor {

    void process(Element element);

    default void updateRanges(Map<String, Range> ranges, Boolean second) {}
}
