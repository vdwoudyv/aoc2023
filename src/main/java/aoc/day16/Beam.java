package aoc.day16;

import aoc.util.Coordinate;
import aoc.util.Direction;

import java.util.ArrayList;
import java.util.List;

public class Beam {

    private final List<CellTraversal> path = new ArrayList<>();
    private final Field field;

    public Beam(Field field) {
        this.field = field;
    }

    public void constructPath(CellTraversal initial) {
        path.add(initial);
        Coordinate current = initial.coordinate();
        Direction currentDirection = initial.direction();

        Coordinate next = current.getAdjacent(currentDirection);
        List<Direction> targetDirections;
        if (field.inBorders(next)) {
            Mirror m = field.getMirror(next);
            targetDirections = m.targetDirections(currentDirection);
            if (targetDirections.size() == 1) {
                CellTraversal t = new CellTraversal(targetDirections.get(0), next);
                if (field.getBeam(t) == null) {
                    field.register(t, this);
                    constructPath(t);
                }
            } else {
                for (Direction targetDirection : targetDirections) {
                    CellTraversal t = new CellTraversal(targetDirection, next);
                    if (field.getBeam(t) == null) {
                        field.addBeam(t);
                    }
                }
            }
        }
    }
}
