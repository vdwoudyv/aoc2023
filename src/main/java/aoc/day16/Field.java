package aoc.day16;

import aoc.util.Coordinate;
import aoc.util.Direction;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Field {

    private Mirror[][] field;
    Map<CellTraversal, Beam> beams = new HashMap<>();


    public Field(List<String> input, CellTraversal initial) {
        field = new Mirror[input.size()+2][input.get(0).length()+2];
        Arrays.fill(field[0], Mirror.DOT);
        Arrays.fill(field[field.length-1], Mirror.DOT);
        for (int i = 0; i < input.size(); i++) {
                char[] chars = ("." + input.get(i) + ".").toCharArray();
                for (int j = 0; j < chars.length; j++) {
                    field[i+1][j] = Mirror.fromValue(chars[j]);
                }
            }
        addBeam(initial);
    }

    public long getNumberOfEnergizedCells() {
        return beams.keySet()
                .stream()
                .map(CellTraversal::coordinate)
                .distinct()
                .filter(c -> c.x() > 0 && c.x() < field[0].length-1)
                .filter(c -> c.y() > 0 && c.y() < field.length-1)
                .count();
    }

    public String energizedString() {
        List<Coordinate> coordinates = beams.keySet().stream().map(CellTraversal::coordinate).collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (coordinates.contains(new Coordinate(j, i))) {
                    sb.append("#");
                } else {
                    sb.append(".");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public void register(CellTraversal traversal, Beam beam) {
        beams.put(traversal, beam);
    }

    public Beam getBeam(CellTraversal traversal) {
        return beams.get(traversal);
    }

    public boolean inBorders(Coordinate coordinate) {
        return coordinate.y() >= 0 && coordinate.y() < field.length &&
                coordinate.x() >= 0 && coordinate.x() < field[0].length;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                sb.append(field[i][j]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public Mirror getMirror(Coordinate next) {
        return field[next.y()][next.x()];
    }

    public void addBeam(CellTraversal t) {
        Beam beam = new Beam(this);
        beams.put(t, beam);
        beam.constructPath(t);
    }
}
