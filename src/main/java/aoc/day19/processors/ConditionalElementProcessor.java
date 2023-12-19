package aoc.day19.processors;

import aoc.day19.Element;
import aoc.day19.Range;

import java.util.Map;
import java.util.function.Function;


public class ConditionalElementProcessor implements ElementProcessor {

    private final char operation;
    private final char operand;
    private final int argument;
    private Function<Element, Long> fetchValue;

    private ElementProcessor onFalse;
    private ElementProcessor onTrue;


    public ConditionalElementProcessor(char operation,
                                       char operand,
                                       int argument,
                                       Function<Element, Long> fetchOperation,
                                       ElementProcessor onFalse,
                                       ElementProcessor onTrue
                                       ) {
        this.operation = operation;
        this.operand = operand;
        this.argument = argument;
        this.onFalse = onFalse;
        this.onTrue = onTrue;
        this.fetchValue = fetchOperation;
    }

    public void process(Element element) {
        switch (operation) {
            case ('<') -> {
                if (fetchValue.apply(element) < argument) {
                    onTrue.process(element);
                } else {
                    onFalse.process(element);
                };
            }
            case ('>') -> {
                if (fetchValue.apply(element) > argument) {
                    onTrue.process(element);
                } else {
                    onFalse.process(element);
                };
            }
            default -> throw new IllegalArgumentException("Unknown operation: " + operation);
            }
        }

    public ElementProcessor getOnFalse() {
        return onFalse;
    }

    public ElementProcessor getOnTrue() {
        return onTrue;
    }

    @Override
    public String toString() {
        return "" + operand + operation + argument;
    }

    @Override
    public void updateRanges(Map<String, Range> ranges, Boolean second) {
        Range relevant = ranges.get("" + operand);
        Range newRange;
        if (operation == '<') { // < 400 means: upper bound is 399. !< 400 means: lower bound is 400
            newRange = second ? new Range(relevant.min(), argument -1) : new Range(argument, relevant.max());
        } else { // > 400 means: lower bound is 401. !> 400 means upper bound is 400
            newRange = second ? new Range(argument + 1, relevant.max()) : new Range(relevant.min(), argument);
        }
        ranges.put("" + operand, newRange);
    }
}
