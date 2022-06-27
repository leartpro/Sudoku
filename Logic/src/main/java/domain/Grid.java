package domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Grid {
    private Field[][] grid;

    public Grid() {
        grid = new Field[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                grid[x][y] = new Field(x, y, 0);
            }
        }
    }

    public boolean isSolved() {
        boolean isSolved = true;
        for (int x = 0; x < 9; x++) {
                Point[] horizontal = new Row(x, x, 0, 8).asPoints();
            if (isRowSolved(horizontal)) return false;
        }
        for (int y = 0; y < 9; y++) {
            Point[] vertical = new Row(0, 8, y, y).asPoints();
            if (isRowSolved(vertical)) return false;
        }

        return isSolved;
    }

    private boolean isRowSolved(Point[] vertical) {
        Field[] VRow = new Field[9];
        int i = 0;
        for (Point p : vertical) {
            VRow[i] = grid[p.x()][p.y()];
            i++;
        }
        if(!isRowCompleted(VRow)) {
            return true;
        }
        return false;
    }

    private boolean isRowCompleted(Field[] row) {
        boolean isCompleted = false;

        return isCompleted;
    }

    public List<Field> solve() {
        List<Field> changes = new ArrayList<>();

        return changes;
    }

    public List<Field> compareTo(Grid grid) {
        List<Field> differences = new ArrayList<>();

        return differences;
    }

    public String toString() {
        return Arrays.stream(grid).toList().toString();
    }
}