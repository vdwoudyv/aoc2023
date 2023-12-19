package aoc.day19;

import aoc.day19.processors.*;
import aoc.util.Tuple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ElementWorld {

    private final Map<String, ElementProcessor> processorMap = new HashMap<>();

    private final List<Element> acceptedElements = new ArrayList<>();
    private final List<Element> rejectedElements = new ArrayList<>();

    public ElementWorld(List<String> processorDefinitions) {
        processorMap.put("A", new AcceptProcessor(this));
        processorMap.put("R", new RejectionProcessor(this));
        configureProcessors(processorDefinitions);
    }

    private void configureProcessors(List<String> processorDefinitions) {
        for (String definition : processorDefinitions) {
            String[] split = definition.split("[{}]");
            String name = split[0];
            String[] operations = split[1].split(",");
            ElementProcessor current = null;
            for (int i = operations.length - 1; i >= 0; i--) {
                String op = operations[i];
                if (op.contains(":")) {
                    Function<Element, Long> fetcher = switch (op.charAt(0)) {
                        case 'a' -> Element::a;
                        case 'x' -> Element::x;
                        case 's' -> Element::s;
                        case 'm' -> Element::m;
                        default -> throw new IllegalArgumentException("Unknown fetcher: " + op.charAt(0));
                    };
                    char operation = op.charAt(1);
                    String[] arguments = op.substring(2).split(":");
                    int argument = Integer.parseInt(arguments[0]);
                    ElementProcessor onTrue = getRedirectionProcessor(arguments[1]);
                    current = new ConditionalElementProcessor(operation, op.charAt(0), argument, fetcher, current, onTrue);
                } else {
                    current = getRedirectionProcessor(op);
                }
            }
            processorMap.put(name, current);
        }
    }

    private ElementProcessor getRedirectionProcessor(String name) {
        if (!processorMap.containsKey("redirect_" + name)) {
            processorMap.put("redirect_" + name, new RedirectionProcessor(this, name));
        }

        return processorMap.get("redirect_" + name);
    }

    public void processElements(List<Element> elements) {
        for (Element e : elements) {
            processorMap.get("in").process(e);
        }
    }

    public void accept(Element e) {
        acceptedElements.add(e);
    }

    public void reject(Element e) {
        rejectedElements.add(e);
    }

    public ElementProcessor getProcessor(String name) {
        return processorMap.get(name);
    }

    public List<Element> getAcceptedElements() {
        return acceptedElements;
    }

    public List<Element> getRejectedElements() {
        return rejectedElements;
    }

    public List<Path> getAllPathsToAcceptance() {
        return getAllPathsToAcceptance(new Path(null, null, processorMap.get("in")));
    }

    public List<Path> getAllPathsToAcceptance(Path activePath) {
        ElementProcessor current = activePath.getLastElement();
        if (current instanceof RejectionProcessor) {
            return new ArrayList<>();
        } else if (current instanceof AcceptProcessor) {
            return List.of(activePath);
        } else if (current instanceof RedirectionProcessor redirector) {
            return getAllPathsToAcceptance(new Path(activePath.getPrevious(), activePath.getChoice(), redirector.getRedirect()));
        } else if (current instanceof ConditionalElementProcessor conditional) {
            List<Path> result = new ArrayList<>();
            result.addAll(getAllPathsToAcceptance(new Path(activePath, new Tuple<>(current, true), conditional.getOnTrue())));
            result.addAll(getAllPathsToAcceptance(new Path(activePath, new Tuple<>(current, false), conditional.getOnFalse())));
            return result;
        } else {
            throw new IllegalArgumentException("Unknown processor type: " + current.getClass());
        }

    }

}

