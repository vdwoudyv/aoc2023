package aoc.day19.processors;

import aoc.day19.Element;
import aoc.day19.ElementWorld;

public class AcceptProcessor implements ElementProcessor {

    private ElementWorld world;

    public AcceptProcessor(ElementWorld world) {
        this.world = world;
    }
    @Override
    public void process(Element element) {
        world.accept(element);
    }

    @Override
    public String toString() {
        return "A";
    }
}
