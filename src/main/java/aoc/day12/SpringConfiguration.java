package aoc.day12;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpringConfiguration {

    private final Map<SearchState, Long> memoizationMap = new HashMap<>();

    public long getNumberOfMatches(String input, List<Integer> sizes) {

        return getNumberOfMatches(new SearchState((input + '.').toCharArray(), sizes));
    }

    public long getNumberOfMatches(SearchState state) {
        if (memoizationMap.containsKey(state)) {
            return memoizationMap.get(state);
        } else {
            long result = -1;
            if (state.inputParsed()) {
                if (state.getCurrentGroupLength() == 0 && state.allGroupsUsed()) {
                    // Everything is parsed and we have a valid solution
                    result = 1;
                } else {
                    result = 0;
                }
            } else {
                switch(state.currentChar()) {
                    case '.' -> {
                        result = caseOperational(state);
                    }
                    case '#' -> {
                        result = caseDamaged(state);
                    }
                    case '?' -> {
                        result = caseOperational(state) + caseDamaged(state);
                    }
                }
            }
            if (result == -1) {
                throw new IllegalArgumentException("This is odd....");
            }
            memoizationMap.put(state, result);
            return result;
        }
    }

    private long caseDamaged(SearchState state) {
        long result;
        if (state.allGroupsUsed() || state.currentGroupFull()) {
            result = 0;
        } else {
            result = getNumberOfMatches(
                    new SearchState(
                            state.getInput(),
                            state.getInputIndex() + 1,
                            state.getGroups(),
                            state.getGroupIndex(),
                            state.getCurrentGroupLength() + 1));
        }
        return result;
    }

    private long caseOperational(SearchState state) {
        if (state.getCurrentGroupLength() > 0) {
            // We are in the middle of a group, and we have just encountered a dot.
            // If the group is the right size, close the group and start anew:
            if (state.getCurrentGroupLength() == state.getRequiredCurrentGroupSize()) {
                return getNumberOfMatches(
                        new SearchState(
                                state.getInput(),
                                state.getInputIndex() + 1,
                                state.getGroups(),
                                state.getGroupIndex() + 1,
                                0));
            } else {
                // Invalid. Exit.
                return 0;
            }
        } else {
                // We are in dots, and continue in dots.
                return getNumberOfMatches(
                        new SearchState(
                                state.getInput(),
                                state.getInputIndex() + 1,
                                state.getGroups(),
                                state.getGroupIndex(),
                                state.getCurrentGroupLength()));
            }
        }
}
