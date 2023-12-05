package aoc;

import aoc.day1.Day1;
import aoc.day2.Day2;
import aoc.day3.Day3;
import aoc.day4.Day4;
import aoc.day5.Day5;

import java.util.List;


public class Main {

    public static void main(String[] args) {
        List<Day> days = List.of(
                /*new Day1(),
                new Day2(),
                new Day3(),
                new Day4(),*/
                new Day5()
        );
        boolean testMode = false;

        for (Day d: days) {
            System.out.println(d.getClass().getSimpleName());
            System.out.println("Part One: " + d.runPartOne(testMode));
            System.out.println("Part Two: " + d.runPartTwo(testMode));
        }
    }
}
