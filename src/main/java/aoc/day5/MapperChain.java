package aoc.day5;

import java.util.ArrayList;
import java.util.List;

public class MapperChain {

    public List<Mapper> mappers = new ArrayList<>();

    public void addMapper(Mapper mapper) {
        mappers.add(mapper);
    }

    public long convert(long input) {
        long result = input;
        for (Mapper mapper: mappers) {
            result = mapper.convert(result);
        }
        return result;
    }
}
