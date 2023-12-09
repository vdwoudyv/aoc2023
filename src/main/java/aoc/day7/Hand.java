package aoc.day7;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Hand implements Comparable<Hand> {

    private HandType handType;
    private String stringRepresentation;
    private long handValue;
    private long bid;

    public Hand(String input, boolean withJokers) {
        stringRepresentation = input;
        String[] parts = input.split(" ");
        parseInput(parts[0].trim(), withJokers);
        bid = Integer.parseInt(parts[1].trim());
    }


    @Override
    public int compareTo(Hand o) {
        if (handType.getHandValue() != o.handType.getHandValue()) {
            return Long.compare(handType.getHandValue(), o.handType.getHandValue());
        } else {
            return Long.compare(handValue, o.handValue);
        }
    }

    private void parseInput(String input, boolean withJoker) {
        handType = calculateHandType(input, withJoker);
        handValue = Long.parseLong(input.chars()
                .mapToObj(c -> "" + ((char) c))
                .map(s -> toNum(s, withJoker))
                .collect(Collectors.joining()));
    }

    private HandType calculateHandType(String input, boolean withJoker) {
        List<List<String>> groups = convertInputToGroups(input, withJoker);
        int firstGroupSize = groups.get(0).size();
        int secondGroupSize = groups.size() > 1 ? groups.get(1).size() : 0;

        return switch (firstGroupSize) {
            case 5 -> HandType.FIVE_OF_A_KIND;
            case 4 -> HandType.FOUR_OF_A_KIND;
            case 3 -> secondGroupSize == 2 ? HandType.FULL_HOUSE : HandType.THREE_OF_A_KIND;
            case 2 -> secondGroupSize == 2 ? HandType.TWO_PAIRS : HandType.ONE_PAIR;
            default -> HandType.HIGH_CARD;
        };
    }

    /**
     * Utility method to convert the input to a list of groups of cards, sorted by size
     *
     * @param input     the input
     * @param withJoker whether 'J' should be interpreted as a Joker or a Jack.
     */
    private List<List<String>> convertInputToGroups(String input, boolean withJoker) {
        List<List<String>> groups = new ArrayList<>(input.chars()
                .mapToObj(c -> "" + ((char) c))
                .collect(Collectors.groupingBy(b -> b))
                .values());
        groups.sort((o1, o2) -> o2.size() - o1.size());

        if (withJoker) {
            Optional<List<String>> jokerGroup = groups.stream().filter(g -> g.get(0).equals("J")).findFirst();
            jokerGroup.ifPresent(jGroup -> {
                if (groups.size() > 1) {
                    groups.remove(jGroup);
                    groups.get(0).addAll(jGroup);
                }
            });
        }
        return groups;
    }

    public long getBid() {
        return bid;
    }

    private String toNum(String c, boolean withJoker) {
        return switch (c) {
            case "A" -> "14";
            case "K" -> "13";
            case "Q" -> "12";
            case "J" -> withJoker ? "00" : "11";
            case "T" -> "10";
            default -> "0" + c;
        };
    }

    @Override
    public String toString() {
        return stringRepresentation + "(" +
                handValue + ")";
    }
}
