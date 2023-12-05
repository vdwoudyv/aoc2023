package aoc.day4;

import aoc.Day;
import aoc.day3.Item;
import aoc.day3.Number;
import aoc.day3.Symbol;
import aoc.util.AocTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day4 implements Day {

    public String runPartOne(boolean testInput) {
        return "" +
                AocTools.read(testInput, this)
                        .stream()
                        .map(Card::new)
                        .mapToDouble(Card::getScore)
                        .sum();
    }

    @Override
    public String runPartTwo(boolean testInput) {
        List<Card> cards = AocTools.read(testInput, this).stream().map(Card::new).toList();
        Map<Integer, Integer> quantities = new HashMap<>();
        cards.forEach(c->quantities.put(c.getId(), 1));

        for (Card c: cards) {
            long numberOfWinningNumbers = c.getNumberOfWinningNumbers();
            for (int i = c.getId() + 1; i < c.getId() + numberOfWinningNumbers + 1; i++) {
                quantities.put(i, quantities.get(i) + quantities.get(c.getId()));
            }
        }

        return "" +
                quantities.values()
                        .stream()
                        .mapToInt(Integer::intValue)
                        .sum();
    }
}
