package aoc.day5;

import java.util.ArrayList;
import java.util.List;

public class Mapper {

    private final String fromType;
    private final String toType;
    private final List<MapElement> elements = new ArrayList<>();

    public Mapper(String fromType, String toType) {
        this.fromType = fromType;
        this.toType = toType;
    }

    public void addRange(long destinationRangeStart, long sourceRangeStart, long length) {
            elements.add(new MapElement(sourceRangeStart, destinationRangeStart, length));
    }

    public long convert(long source) {
        for (MapElement element: elements) {
            if (element.matches(source)) {
                return element.map(source);
            }
        }
        return source;
    }
}
