package aoc.day15;

import aoc.Day;
import aoc.util.AocTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Day15 implements Day {

    @Override
    public boolean forceTest() {
        return false;
    }

    @Override
    public String runPartOne(boolean testInput) {
        return "" + AocTools.read(testInput, this).stream().flatMap(s -> Stream.of(s.split(","))).mapToLong(this::computeHash).sum();
    }

    @Override
    public String runPartTwo(boolean testInput) {
        HashMap<Long, List<Lens>> boxes = new HashMap<>();

        AocTools.read(testInput, this).stream().flatMap(s -> Stream.of(s.split(","))).forEach(command -> {
            int locationOfEquals = command.indexOf("=");
            if (locationOfEquals != -1) {
                String label = command.substring(0, locationOfEquals);
                int focalLength = Integer.parseInt(command.substring(locationOfEquals + 1).trim());
                long boxNumber = computeHash(label);
                List<Lens> boxContent = boxes.getOrDefault(boxNumber, new ArrayList<>());
                Optional<Lens> existingLens = boxContent.stream().filter(l -> l.label().equals(label)).findFirst();
                if (existingLens.isPresent()) {
                    existingLens.get().setFocalLength(focalLength);
                } else {
                    boxContent.add(new Lens(label, focalLength));
                }
                boxes.put(boxNumber, boxContent);
            } else {
                String label = command.substring(0,command.indexOf("-"));
                long boxNumber = computeHash(label);
                List<Lens> boxContent = boxes.getOrDefault(boxNumber, new ArrayList<>());
                boxContent.removeIf(l -> l.label().equals(label));
                boxes.put(boxNumber, boxContent);
            }
        });
        long result = 0;
        for (long boxNumber : boxes.keySet()) {
        for (int i = 0; i < boxes.get(boxNumber).size(); i++) {
            Lens lens = boxes.get(boxNumber).get(i);
            long focusPower = ((i+1) * (1+boxNumber) * lens.getFocalLength());
            result = result + focusPower;
        }
        }
        return "" + result;
    }


    public long computeHash(String s) {
        long hash = 0;
        for (int i = 0; i < s.length(); i++) {
            hash = (hash + s.charAt(i))*17 % 256;
        }
        return hash;
    }

}
