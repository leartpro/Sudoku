package utils;

import domain.Field;

import java.util.List;

public class Solver extends GridUtils{
    private final Field[][] grid;

    public Solver(Field[][] grid) {
        this.grid = grid;
    }

    public boolean isSolved() {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                Field current = grid[x][y];

                //check row for current
                for (int i = 0; i < 9; i++) {
                    if(i == current.y()) {
                        continue;
                    }
                    if (grid[x][i].value() == current.value()) return false;
                }

                //check column for current
                for (int i = 0; i < 9; i++) {
                    if(i == current.x()) {
                        continue;
                    }
                    if (grid[i][y].value() == current.value()) return false;
                }

                //check square for current
                int squareXStart = x - x % 3;
                int squareYStart = y - y % 3;
                for (int i = squareXStart; i < squareXStart + 3; i++) {
                    for (int j = squareYStart; j < squareYStart + 3; j++) {
                        if (i == current.x() && y == current.y()) continue;
                        if (grid[i][j].value() == current.value()) return false;
                    }
                }

            }
        }
        return true;
    }

    public List<Field> solve() {
        Field[][] solution = new Field[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                solution[x][y] = new Field(x, y, grid[x][y].value());
            }
        }
        if (calculateSolution(solution)) {
            return compareTo(solution, this.grid);
        }
        return null;
    }

    public boolean isUnique(Field[][] grid, Field insert) {
        for (int y = 0; y < 9; y++) {
            if (grid[insert.x()][y].value() == insert.value()) return false;
        }
        for (int x = 0; x < 9; x++) {
            if (grid[x][insert.y()].value() == insert.value()) return false;
        }

        int squareXStart = insert.x() - insert.x() % 3;
        int squareYStart = insert.y() - insert.y() % 3;

        for (int x = squareXStart; x < squareXStart + 3; x++) {
            for (int y = squareYStart; y < squareYStart + 3; y++) {
                if (grid[x][y].value() == insert.value()) return false;
            }
        }
        return true;
    }

    private boolean calculateSolution(Field[][] grid) { //n = grid.length
        int xPos = -1;
        int yPos = -1;
        boolean completed = true;
        int x = 0;
        while (x < 9 && completed) {
            int y = 0;
            while (y < 9 && completed) {
                if (grid[x][y].value() == 0) {
                    completed = false;
                    xPos = x;
                    yPos = y;
                }
                y++;
            }
            x++;
        }
        if (completed) return true;
        //backtrack
        for (int value = 1; value <= 9; value++) {
            assert (grid[xPos][yPos].value() == 0); //may cause error
            if (isUnique(grid, new Field(xPos, yPos, value))) {
                grid[xPos][yPos] = new Field(xPos, yPos, value);
                if (calculateSolution(grid)) return true;
                else grid[xPos][yPos] = new Field(xPos, yPos, 0); // replace
            }
        }
        return false;
    }
}
