package aoc.day19.processors;

import aoc.day19.Element;
import aoc.day19.ElementWorld;
import aoc.day19.processors.ElementProcessor;

public class RejectionProcessor implements ElementProcessor {

    private ElementWorld world;

    public RejectionProcessor(ElementWorld world) {
        this.world = world;
    }

    @Override
    public void process(Element element) {
        world.reject(element);
    }

    @Override
    public String toString() {
        return "R";
    }
}
