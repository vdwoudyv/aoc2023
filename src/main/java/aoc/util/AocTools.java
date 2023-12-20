package aoc.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AocTools {

    /**
     * Reads the contents of the input file into an array of strings
     * @param test whether you want the inputfile (Input.txt) or the testfile (TestInput1.txt). Always from the folder
     *             of the calling class
     * @param requester the requester of the file. Used to determine the desired location of the file.
     * @return the contents of the file in a list.
     */
    public static List<String> read(boolean test, Object requester) {
        return read(test ? "TestInput.txt" : "Input.txt", requester);
    }

    public static List<String> read(String givenFileName, Object requester) {
        String name = requester.getClass().getCanonicalName();
        String fileName = "src/main/java/" + name.substring(0, name.lastIndexOf(".")).replaceAll("\\.", "/")
                + "/" + givenFileName;
        try (Stream<String> content = Files.lines(Path.of(fileName))) {
            return content.collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Splits the list of strings in multiple lists based on a generic split condition to seperate a group from the
     * previous/subsequent
     * @param input the inputlist
     * @param splitCondition a test that indicates whether a certain line is a marker to switch to a new group
     * @param addToPrevious if true, the marker will be added to the previous group
     * @param addToNext if true, the marker will be added to the next group
     * @return a lists of lists
     */
    public static List<List<String>> getGroups(List<String> input,
                                               Predicate<String> splitCondition,
                                               boolean addToPrevious,
                                               boolean addToNext) {
        List<List<String>> result = new ArrayList<>();
        List<String> current = new ArrayList<>();
        for (String i: input) {
            if (splitCondition.test(i)) {
                if (addToPrevious) {
                    current.add(i);
                }
                if (! current.isEmpty()) {
                    result.add(current);
                    current = new ArrayList<>();
                }
                if (addToNext) {
                    current.add(i);
                }
            } else {
                current.add(i);
            }
        }
        if (!current.isEmpty()) {
            result.add(current);
        }
        return result;
    }

    /**
     * Splits the list of strings in multiple lists, where each empty line seperates a group from the previous/subsequent.
     * The empty lines between the groups are not in the output
     * @param input the inputlist
     * @return a lists of lists
     */
    public static List<List<String>> getGroups(List<String> input) {
        return getGroups(input, String::isBlank, false, false);
    }



    /**
     * Parses the input as a list of lists of T. Each row is split into columns by the tokenizer, and each column is
     * converted to a T by the generator
     * @param input the input
     * @param generator the function that converts a string to a T
     * @param tokenizer the function that splits a string into columns
     * @return a list of lists of T
     * @param <T> the type of the result
     */
    public static <T> List<List<T>> parseAsArray(List<String> input,
                                         Function<String, T> generator,
                                         Function<String, List<String>> tokenizer) {
        List<List<T>> result = new ArrayList<>();
        for (String row: input) {
            List<T> rowResult = new ArrayList<>();
            List<String> columns = tokenizer.apply(row);
            for (String column: columns) {
                rowResult.add(generator.apply(column));
            }
            result.add(rowResult);
        }
        return result;
    }

    /**
     * Specific version of the generic parseAsArray, where each row is a string and each character is a column.
     */
    public static <T> List<List<T>> parseAsArray(List<String> input, Function<String, T> generator) {
        return parseAsArray(input, generator, s -> s.chars()
                .mapToObj(c -> "" + (char) c)
                .collect(Collectors.toList()));
    };

    public static <T> List<T> getNeighBours(List<List<T>> input, int x, int y) {
        List<T> result = new ArrayList<>();
        if (x > 0) {
            result.add(input.get(y).get(x-1));
        }
        if (x < input.get(0).size()-1) {
            result.add(input.get(y).get(x+1));
        }
        if (y > 0) {
            result.add(input.get(y-1).get(x));
        }
        if (y < input.size()-1) {
            result.add(input.get(y+1).get(x));
        }
        return result;
    }

    public static BigDecimal leastCommonMultiple(List<BigDecimal> values) {
        BigDecimal acc = leastCommonMultiple(values.get(0), values.get(1));
        for (int i = 2; i < values.size(); i++) {
            acc = leastCommonMultiple(acc, values.get(i));
        }
        return acc;
    }

    public static long leastCommonMultipleLong(List<Long> values) {
        return leastCommonMultiple(values.stream().map(l -> new BigDecimal("" + l)).collect(Collectors.toList())).longValue();
    }


    private static BigDecimal leastCommonMultiple(BigDecimal first, BigDecimal second) {
        if (first == BigDecimal.ZERO || second == BigDecimal.ZERO) {
            return BigDecimal.ZERO;
        }
        BigDecimal highest = first.compareTo(second) <= 0 ? second : first;
        BigDecimal lowest = first.compareTo(second) <= 0 ? first : second;
        BigDecimal result = highest;
        while (result.remainder(lowest).compareTo(BigDecimal.ZERO) != 0) {
            result = result.add(highest);
        }
        return result;
    }
}
