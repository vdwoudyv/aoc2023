package aoc.day4;

import java.util.List;
import java.util.stream.Stream;

public class Card {

    private final int id;
    private final List<Integer> winningNumbers;
    private final List<Integer> myNumbers;

    public Card(String input) {
        String[] parts = input.substring(4).trim().split(":");
        id = Integer.parseInt(parts[0]);
        String[] numbers = parts[1].trim().split("\\|");
        winningNumbers = parseNumbers(numbers[0]);
        myNumbers = parseNumbers(numbers[1]);
    }

    private List<Integer> parseNumbers(String number) {
        return Stream.of(number.split(" ")).map(String::trim).filter(s->!s.isBlank()).map(Integer::parseInt).toList();
    }

    public long getNumberOfWinningNumbers() {
        return myNumbers.stream().filter(winningNumbers::contains).count();
    }

    public double getScore() {
        List<Integer> intersection = myNumbers.stream().filter(winningNumbers::contains).toList();
        return intersection.isEmpty()? 0.0 : Math.pow(2.0, intersection.size()-1);
    }

    public int getId() {
        return id;
    }
}
