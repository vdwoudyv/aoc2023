package aoc.day2;

import aoc.Day;
import aoc.util.AocTools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day2 implements Day {

    public String runPartOne(boolean testInput) {
        List<Game> games = AocTools.read(testInput, this).stream()
                .map(this::parseGame)
                .toList();
        Map<String, Integer> numberOfStones = Map.of(
                "red", 12,
                "green", 13,
                "blue", 14
        );
       return "" + games.stream().filter(g->g.isPossible(numberOfStones)).mapToInt(Game::id).sum();
    }

    private Game parseGame(String s) {
        String[] parts = s.substring(5).split(":");
        int id = Integer.parseInt(parts[0]);
        List<Draw> draws = Stream.of(parts[1].split(";"))
                .map(this::parseDraw)
                .collect(Collectors.toList());
        return new Game(id, draws);
    }

    private Draw parseDraw(String s) {
        String[] parts = s.split(",");
        Map<String, Integer> colorMap = new HashMap<>();
        Stream.of(parts).forEach(p -> {
            String[] colorParts = p.trim().split(" ");
            colorMap.put(colorParts[1].trim(), Integer.parseInt(colorParts[0].trim()));
        });
        return new Draw(colorMap);
    }

    @Override
    public String runPartTwo(boolean testInput) {
        int totalPower = AocTools.read(testInput, this).stream()
                .map(this::parseGame)
                .mapToInt(Game::power)
                .sum();
        return "" + totalPower;
    }


}
