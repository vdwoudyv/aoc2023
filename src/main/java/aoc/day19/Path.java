package aoc.day19;

import aoc.day19.processors.ElementProcessor;
import aoc.util.Tuple;

import java.util.HashMap;
import java.util.Map;

public class Path{
    private final Path previous;
    private final Tuple<ElementProcessor, Boolean> choice;
    private final ElementProcessor lastElement;

    public Path(Path previous, Tuple<ElementProcessor, Boolean> lastChoice, ElementProcessor current) {
        this.previous = previous;
        this.choice = lastChoice;
        this.lastElement = current;
    }

    public boolean contains(ElementProcessor processor) {
        return lastElement == processor || (previous != null && previous.contains(processor));
    }

    public Path getPrevious() {
        return previous;
    }

    public Tuple<ElementProcessor, Boolean> getChoice() {
        return choice;
    }

    public ElementProcessor getLastElement() {
        return lastElement;
    }

    public Map<String, Range> getRanges(long initMin, long initMax) {
        Map<String, Range> ranges;
        if (previous == null) {
            ranges = new HashMap<>();
            ranges.put("a", new Range(initMin, initMax));
            ranges.put("x", new Range(initMin, initMax));
            ranges.put("s", new Range(initMin, initMax));
            ranges.put("m", new Range(initMin, initMax));
        } else {
            ranges = previous.getRanges(initMin, initMax);
            choice.first().updateRanges(ranges, choice.second());
        }
        return ranges;
    }

    public long nbPossibilities() {
        long result = 1;
        for (Range r: getRanges(1, 4000).values()) {
            result *= r.nbPossibilities();
        }
        return result;
    }
}
