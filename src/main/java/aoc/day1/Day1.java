package aoc.day1;

import aoc.Day;
import aoc.util.AocTools;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Day1 implements Day {

    public String runPartOne(boolean testInput) {
        Long result = AocTools.read(testInput, this).stream()
                // throw away all non digits: ^ is a negation
                .map(s -> s.replaceAll("[^\\d]", ""))
                .map(s -> "" + s.charAt(0) + s.charAt(s.length() - 1))
                .mapToLong(Long::parseLong)
                .sum();
        return "" + result;
    }

    @Override
    public String runPartTwo(boolean testInput) {
        Long result = AocTools.read(testInput, this).stream()
                // throw away all non digits: ^ is a negation
                .map(this::swapWrittenNumbers)
                .map(s -> s.replaceAll("[^\\d]", ""))
                .map(s -> "" + s.charAt(0) + s.charAt(s.length() - 1))
                .mapToLong(Long::parseLong)
                .sum();
        return "" + result;
    }

    private String swapWrittenNumbers(String s) {
        // Essential is that each textual number is replaced by something that contains exactly ones the digit of that
        // number. We keep the initial text of the number, in order not to influence numbers immediately after it.
        // for example, oneight would swap to one1oneight8eight, which after removal of characters, is 18.
        // Alternatively, we can create an atomic swap where all swaps are done at once, but that would require a
        // complex regex or algorithm not needed for this simple exercise. Keep it simple.
        return s.replaceAll("one", "one1one")
                .replaceAll("two", "two2two")
                .replaceAll("three", "three3three")
                .replaceAll("four", "four4four")
                .replaceAll("five", "five5five")
                .replaceAll("six", "six6six")
                .replaceAll("seven", "seven7seven")
                .replaceAll("eight", "eight8eight")
                .replaceAll("nine", "nine9nine");
    }
}
