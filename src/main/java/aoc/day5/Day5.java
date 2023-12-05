package aoc.day5;

import aoc.Day;
import aoc.util.AocTools;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day5 implements Day {

    @Override
    public String runPartOne(boolean testInput) {
        List<List<String>> input = AocTools.getGroups(AocTools.read(testInput, this));
        MapperChain mapperChain = new MapperChain();
        for (int i = 1; i < input.size(); i++){
            mapperChain.addMapper(constructMapper(input.get(i)));
        }
        return "" + Stream.of(input.get(0).get(0).split(":")[1].split(" "))
                .map(String::trim)
                .filter(s->!s.isBlank())
                .map(Long::parseLong)
                .mapToLong(mapperChain::convert)
                .min();
    }

    private Mapper constructMapper(List<String> strings) {
        String name = strings.get(0);
        String fromType = name.substring(0, name.indexOf("-"));
        String toType = name.substring(name.indexOf("-to-") + 4, name.indexOf(' '));
        Mapper mapper = new Mapper(fromType, toType);
        for (int i = 1; i < strings.size(); i++) {
            String[] parts = strings.get(i).split(" ");
            mapper.addRange(Long.parseLong(parts[0]), Long.parseLong(parts[1]), Long.parseLong(parts[2]));
        }
        return mapper;
    }

    @Override
    public String runPartTwo(boolean testInput) {
        List<List<String>> input = AocTools.getGroups(AocTools.read(testInput, this));
        MapperChain mapperChain = new MapperChain();
        for (int i = 1; i < input.size(); i++){
            mapperChain.addMapper(constructMapper(input.get(i)));
        }
        List<Long> inputs = Stream.of(input.get(0).get(0).split(":")[1].split(" "))
                .map(String::trim)
                .filter(s->!s.isBlank())
                .map(Long::parseLong)
                .toList();

        long lowest = Long.MAX_VALUE;
        for (int i = 0; i < inputs.size(); i+=2) {
            System.out.println("Processing input " + i);
            long initial = inputs.get(i);
            long length = inputs.get(i+1);
            for (long j = initial; j < initial + length; j++) {
                long converted = mapperChain.convert(j);
                if (converted < lowest) {
                    lowest = converted;
                    if (lowest == 0) {
                        return "0";
                    }
                }
            }
        }
        return "" + lowest;
    }
}
