package aoc.day19.processors;

import aoc.day19.Element;
import aoc.day19.ElementWorld;
import aoc.day19.processors.ElementProcessor;

public class RedirectionProcessor implements ElementProcessor {

    private ElementWorld world;
    private final String redirect;

    public RedirectionProcessor(ElementWorld world, String redirect) {
        this.world = world;
        this.redirect = redirect;
    }

    @Override
    public void process(Element element) {
        getRedirect().process(element);
    }

    public ElementProcessor getRedirect() {
        return world.getProcessor(redirect);
    }

    @Override
    public String toString() {
        return "->" + redirect;
    }
}
