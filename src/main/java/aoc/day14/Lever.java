package aoc.day14;

import java.util.List;

public class Lever {

    public Character[][] grid;

    public Lever(List<String> input) {
        grid = new Character[input.size()][input.get(0).length()];
        int lineNumber = 0;
        for (String line: input) {
            for (int i = 0; i < line.length(); i++) {
                grid[lineNumber][i] = line.charAt(i);
            }
            lineNumber++;
        }
    }

    public String toString() {
        StringBuilder b = new StringBuilder();
        for (Character[] line: grid) {
            for (Character c: line) {
                b.append(c);
            }
            b.append("\n");
        }
        return b.toString();
    }

    public void tiltNorth() {
        int[] lastSupportEmpty = new int[grid[0].length];
        for (int i = 0; i < grid[0].length; i++) {
            if (grid[0][i] == 'O' || grid[0][i] == '#') {
                lastSupportEmpty[i] = 0;
            } else {
                lastSupportEmpty[i] = -1;
            }
        }
        for (int j = 1; j < grid.length; j++) {
            for (int i = 0; i < grid[0].length; i++) {
                if (grid[j][i] == 'O') {
                    lastSupportEmpty[i]++;
                    grid[j][i] = '.';
                    grid[lastSupportEmpty[i]][i] = 'O';
                } else if (grid[j][i] == '#'){
                    lastSupportEmpty[i] = j;
                }
            }
        }
    }

    public void tiltSouth() {
        int[] lastSupportEmpty = new int[grid[0].length];
        for (int i = 0; i < grid[0].length; i++) {
            if (grid[grid.length-1][i] == 'O' || grid[grid.length-1][i] == '#') {
                lastSupportEmpty[i] = grid.length-1;
            } else {
                lastSupportEmpty[i] = grid.length;
            }
        }
        for (int j = grid.length - 2 ; j >= 0; j--) {
            for (int i = 0; i < grid[0].length; i++) {
                if (grid[j][i] == 'O') {
                    lastSupportEmpty[i]--;
                    grid[j][i] = '.';
                    grid[lastSupportEmpty[i]][i] = 'O';
                } else if (grid[j][i] == '#'){
                    lastSupportEmpty[i] = j;
                }
            }
        }
    }

    public void tiltEast() {
        int[] lastSupportEmpty = new int[grid.length];
        for (int i = 0; i < grid.length; i++) {
            if (grid[i][grid[0].length-1] == 'O' || grid[i][grid[0].length-1] == '#') {
                lastSupportEmpty[i] = grid[0].length-1;
            } else {
                lastSupportEmpty[i] = grid[0].length;
            }
        }
        for (int j = grid[0].length - 2 ; j >= 0; j--) {
            for (int i = 0; i < grid.length; i++) {
                if (grid[i][j] == 'O') {
                    lastSupportEmpty[i]--;
                    grid[i][j] = '.';
                    grid[i][lastSupportEmpty[i]] = 'O';
                } else if (grid[i][j] == '#'){
                    lastSupportEmpty[i] = j;
                }
            }
        }
    }

    public void tiltWest() {
        int[] lastSupportEmpty = new int[grid.length];
        for (int i = 0; i < grid.length; i++) {
            if (grid[i][0] == 'O' || grid[i][0] == '#') {
                lastSupportEmpty[i] = 0;
            } else {
                lastSupportEmpty[i] = -1;
            }
        }
        for (int j = 1 ; j < grid[0].length; j++) {
            for (int i = 0; i < grid.length; i++) {
                if (grid[i][j] == 'O') {
                    lastSupportEmpty[i]++;
                    grid[i][j] = '.';
                    grid[i][lastSupportEmpty[i]] = 'O';
                } else if (grid[i][j] == '#'){
                    lastSupportEmpty[i] = j;
                }
            }
        }
    }

    public int totalLoad() {
        int total = 0;
        int rows = grid.length;
        for (int row = 1; row <= grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (grid[rows - row][col] == 'O') {
                    total += row;
                }
            }
        }
        return total;
    }

}
