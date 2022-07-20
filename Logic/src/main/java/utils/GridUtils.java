package utils;

import domain.Field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class GridUtils {

    public boolean isUnique(Field[][] grid, Field insert) {
        for (int y = 0; y < 9; y++) if (grid[insert.x()][y].value() == insert.value()) return false;
        for (int x = 0; x < 9; x++) if (grid[x][insert.y()].value() == insert.value()) return false;
        int squareXStart = insert.x() - insert.x() % 3;
        int squareYStart = insert.y() - insert.y() % 3;
        for (int x = squareXStart; x < squareXStart + 3; x++) {
            for (int y = squareYStart; y < squareYStart + 3; y++) {
                if (grid[x][y].value() == insert.value()) return false;
            }
        }
        return true;
    }

    public List<Field> flatGrid(Field[][] grid) {
        List<Field> result = new ArrayList<>();
        for (Field[] array : grid) {
            result.addAll(Arrays.asList(array));
        }
        assert result.size() == 81;
        return result;
    }

    public Field[][] newInstanceOf(Field[][] grid) {
        Field[][] solution = new Field[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                solution[x][y] = new Field(x, y, grid[x][y].value());
            }
        }
        return solution;
    }

    public boolean isSolved(Field[][] grid) {
        for (Field current : flatGrid(grid)) {
            if (current.value() == 0) return false;
            for (int i = 0; i < 9; i++) {
                if (i == current.y()) continue;
                if (grid[current.x()][i].value() == current.value()) return false;
            }
            for (int i = 0; i < 9; i++) {
                if (i == current.x()) continue;
                if (grid[i][current.y()].value() == current.value()) return false;
            }
            int squareXStart = current.x() - current.x() % 3;
            int squareYStart = current.y() - current.y() % 3;
            for (int i = squareXStart; i < squareXStart + 3; i++) {
                for (int j = squareYStart; j < squareYStart + 3; j++) {
                    if (i == current.x()) continue;
                    if (grid[i][j].value() == current.value()) return false;
                }
            }
        }
        return true;
    }
}
