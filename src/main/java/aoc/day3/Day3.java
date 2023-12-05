package aoc.day3;

import aoc.Day;
import aoc.util.AocTools;

import java.util.ArrayList;
import java.util.List;

public class Day3 implements Day {

    public String runPartOne(boolean testInput) {
        List<Item> items = parseItemsFromInput(testInput);

        List<Number> numbers = items.stream().filter(i->i instanceof Number).map(i->(Number)i).toList();
        List<Symbol> symbols = items.stream().filter(i->i instanceof Symbol).map(i->(Symbol)i).toList();

        List<Number> wantedNumbers = numbers.stream().filter(n -> symbols.stream().anyMatch(s -> s.isAdjacent(n))).toList();
        return ""+ wantedNumbers.stream().mapToInt(Number::getNumberValue).sum();
    }

    @Override
    public String runPartTwo(boolean testInput) {
        List<Item> items = parseItemsFromInput(testInput);

        List<Number> numbers = items.stream().filter(i->i instanceof Number).map(i->(Number)i).toList();
        return "" + items.stream()
                .filter(i->i instanceof Symbol)
                .map(i->(Symbol)i)
                .filter(s->s.getSymbol().equals("*"))
                .map(s->numbers.stream().filter(n-> n.isAdjacent(s)).toList())
                .filter(l->l.size() == 2)
                .mapToInt(l->l.get(0).getNumberValue() * l.get(1).getNumberValue())
                .sum();
    }

    private List<Item> parseItemsFromInput(boolean testInput) {
        List<Item> items = new ArrayList<>();
        List<String> lines = AocTools.read(testInput, this);
        for (int i = 0; i < lines.size(); i++) {
            items.addAll(parseItemsFromRow(lines.get(i), i));
        }
        return items;
    }

    private List<Item> parseItemsFromRow(String s, int rowNumber) {
        List<Item> items = new ArrayList<>();
        String current = "";
        boolean numberMode = true;
        int startColumn = 0;
        for (int i = 0; i < s.length(); i++) {
            if (current.isBlank()) {
                startColumn = i;
            }
            char currentChar = s.charAt(i);
            boolean isDigit = Character.isDigit(currentChar);
            if ((currentChar == '.') && (!current.isBlank())) {
                items.add(createItem(rowNumber, startColumn, i-1, current));
                current = "";
            } else {
                if (current.isBlank() || (isDigit && numberMode) || (!isDigit && !numberMode)) {
                    current += currentChar;
                    numberMode = isDigit;
                } else {
                    items.add(createItem(rowNumber, startColumn, i-1, current));
                    current = "" + currentChar;
                    numberMode = isDigit;
                }
            }
        }
        if (!current.isBlank()) {
            items.add(createItem(rowNumber, startColumn, s.length() - 1, current));
        }
        return items;
    }

    private Item createItem(int rowNumber, int startColumn, int endColumn, String content) {
        try {
            int value = Integer.parseInt(content);
            return new Number(rowNumber, startColumn, endColumn, value);
        } catch (NumberFormatException nfe) {
            return new Symbol(rowNumber, startColumn, endColumn, content);
        }
    }
}
