package aoc.day13;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Collections.swap;

public class TerrainPattern {

    private List<String> terrain;
    private List<String> rotated;

    public TerrainPattern(List<String> terrain) {
        this.terrain = terrain;
        this.rotated = rotate(terrain);
    }

    private List<String> rotate(List<String> terrain) {
        List<String> rotated = new ArrayList<>();
        List<char[]> array = terrain.stream().map(String::toCharArray).toList();
        for (int i = 0; i < array.get(0).length; i++) {
            StringBuilder b = new StringBuilder();
            for (int j = 0; j < array.size(); j++) {
                b.append(array.get(j)[i]);
            }
            rotated.add(b.toString());
        }
        return rotated;
    }

    public long getReflectionSummary() {
        return getMirrorLines(rotated).stream().mapToLong(c->c).sum()
                + 100 * getMirrorLines(terrain).stream().mapToLong(c->c).sum();
    }

    public long getReflectionSummaryWithSmudges() {
        List<TerrainPattern> permutations = getPermutations();
        List<Integer> rotatedLines = getMirrorLines(rotated);
        List<Integer> terrainlines = getMirrorLines(terrain);
        Set<Long> results = new HashSet<>();
        for (TerrainPattern pattern: permutations) {
            List<Integer> remainingTerrainLines = pattern.getMirrorLines(pattern.terrain);
            List<Integer> remainingRotatedLines = pattern.getMirrorLines(pattern.rotated);
            remainingTerrainLines.removeAll(terrainlines);
            remainingRotatedLines.removeAll(rotatedLines);
            results.add(remainingRotatedLines.stream().mapToLong(c->c).sum() + 100 * remainingTerrainLines.stream().mapToLong(c->c).sum());
        }
        return results.stream().mapToLong(c->c).sum();
    }

    private List<Integer> getMirrorLines(List<String> toProcess) {
        List<Integer> candidates = new ArrayList<>();
        for (int i = 0; i < toProcess.size() - 1; i++) {
            if (toProcess.get(i).equals(toProcess.get(i+1))) {
                candidates.add(i+1);
            }
        }
        return candidates.stream().filter(c -> isValidMirror(toProcess, c)).collect(Collectors.toList());
    }

    private boolean isValidMirror(List<String> toProcess, Integer axis) {
        int nbSteps = Math.min(axis, toProcess.size() - axis);
        boolean matches = true;
        for (int i = 0; i < nbSteps; i++) {
            matches = matches && toProcess.get(axis - 1 - i).equals(toProcess.get(axis + i));
        }
        return matches;
    }

    @Override
    public String toString() {
        return String.join("\n", terrain) + "\n\n\n" + String.join("\n", rotated);
    }


    public List<TerrainPattern> getPermutations() {
        List<TerrainPattern> result = new ArrayList<>();
        for (int i = 0; i < terrain.size(); i++) {
            for (int j = 0; j < terrain.get(0).length(); j++) {
                ArrayList<String> newTerrain = new ArrayList<>(terrain);
                String modified = swap(terrain.get(i), j);
                newTerrain.set(i, modified);
                result.add(new TerrainPattern(newTerrain));
            }
        }
        return result;
    }

    private String swap(String s, int i) {
        char[] array = s.toCharArray();
        if (array[i] == '.') {
            array[i] = '#';
        } else {
            array[i] = '.';
        }
        return new String(array);
    }
}
