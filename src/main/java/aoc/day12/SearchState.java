package aoc.day12;

import java.util.List;
import java.util.Objects;

public class SearchState {

    private char[] input;
    private int currentGroupLength;
    private List<Integer> groups;

    private int inputIndex;
    private int groupIndex;

    public SearchState(char[] input, List<Integer> groups) {
        this.input = input;
        this.inputIndex = 0;

        this.groups = groups;
        this.groupIndex = 0;
        this.currentGroupLength = 0;
    }

    public SearchState(char[] input, int inputIndex, List<Integer> groups, int groupIndex, int currentGroupLength) {
        this.input = input;
        this.inputIndex = inputIndex;

        this.groups = groups;
        this.groupIndex = groupIndex;
        this.currentGroupLength = currentGroupLength;
    }

    public char[] getInput() {
        return input;
    }


    public boolean allGroupsUsed() {
        return groupIndex == groups.size();
    }

    public char currentChar() {
        return input[inputIndex];
    }

    public int getCurrentGroupLength() {
        return currentGroupLength;
    }

    public List<Integer> getGroups() {
        return groups;
    }

    public Integer getRequiredCurrentGroupSize() {
        return groups.get(groupIndex);
    }

    public int getInputIndex() {
        return inputIndex;
    }

    public int getGroupIndex() {
        return groupIndex;
    }

    public boolean inputParsed() {
        return inputIndex == input.length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchState that = (SearchState) o;
        return currentGroupLength == that.currentGroupLength && inputIndex == that.inputIndex && groupIndex == that.groupIndex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentGroupLength, inputIndex, groupIndex);
    }

    public boolean currentGroupFull() {
        return currentGroupLength >= groups.get(groupIndex);
    }
}
