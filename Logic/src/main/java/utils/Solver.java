package utils;

import domain.Field;

import java.util.ArrayList;
import java.util.List;

public final class Solver extends GridUtils {
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
                    if (i == current.y()) {
                        continue;
                    }
                    if (grid[x][i].value() == current.value()) return false;
                }

                //check column for current
                for (int i = 0; i < 9; i++) {
                    if (i == current.x()) {
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
        Field[][] solution = newInstanceOf(grid);
        if (calculateSolution(solution)) {
            return compareTo(solution, this.grid);
        }
        return null;
    }

    int debugCalculateCount = 0;
    //TODO: to calculate a difficulty for the grid:
    // try first with easy solve-techniques and if there is no solution repeat with more advanced techniques
    private boolean calculateSolution(Field[][] grid) { //n = grid.length
        //TODO: need sometimes a lot of time
        // display progress in user-interface and find better optimisations
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
            assert (grid[xPos][yPos].value() == 0);
            if (isUnique(grid, new Field(xPos, yPos, value))) {
                debugCalculateCount++;
                grid[xPos][yPos] = new Field(xPos, yPos, value);
                if (calculateSolution(grid)) {
                    return true;
                }
                else grid[xPos][yPos] = new Field(xPos, yPos, 0); // replace
            }
        }
        return false;
    }

    public boolean isSolvable() {
        boolean v = calculateSolution(newInstanceOf(grid));
        System.out.println("calculateSolution needed: " + debugCalculateCount + " to calculate"); //Todo: give progress to user-interface
        debugCalculateCount = 0;
        return v;
    }

    public List<Field[][]> allSolutions() { //todo: find each solution twice
        List<Field[][]> solutions = new ArrayList<>();
        @SuppressWarnings("unchecked") List<Integer>[][] values = new ArrayList[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (grid[x][y].value() == 0) {
                    List<Integer> available = new ArrayList<>();
                    for (int i = 0; i < 9; i++) {
                        if (isUnique(grid, new Field(x, y, i))) available.add(i);
                    }
                    values[x][y] = new ArrayList<>(available);
                } else {
                    values[x][y] = new ArrayList<>();
                }
            }
        }
        List<Field> available = new ArrayList<>();
        for (Field current : flatGrid(newInstanceOf(grid))) {
            if (current.value() == 0) available.add(current);
        }
        for (Field current : available) {
            if(values[current.x()][current.y()].size() == 0) continue;
            Field[][] solution = newInstanceOf(grid);
            Integer value = values[current.x()][current.y()].get(0);
            solution[current.x()][current.y()] = new Field(current.x(), current.y(), value);
            values[current.x()][current.y()].remove(value);
            if (calculateSolution(solution)) {
                if (!inList(solutions, solution)) solutions.add(solution);
                //todo: optimize the values[][][] handle
                // so the same solution is never calculated more than once
            }
        }
        return solutions;
    }

    //TODO: returns as early as possible
    public boolean uniqueSolution() { //todo: find each solution twice
        List<Field[][]> solutions = new ArrayList<>();
        @SuppressWarnings("unchecked") List<Integer>[][] values = new ArrayList[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (grid[x][y].value() == 0) {
                    List<Integer> available = new ArrayList<>();
                    for (int i = 0; i < 9; i++) {
                        if (isUnique(grid, new Field(x, y, i))) available.add(i);
                    }
                    values[x][y] = new ArrayList<>(available);
                } else {
                    values[x][y] = new ArrayList<>();
                }
            }
        }
        List<Field> available = new ArrayList<>();
        for (Field current : flatGrid(newInstanceOf(grid))) {
            if (current.value() == 0) available.add(current);
        }
        for (Field current : available) {
            if(values[current.x()][current.y()].size() == 0) continue;
            Field[][] solution = newInstanceOf(grid);
            Integer value = values[current.x()][current.y()].get(0);
            solution[current.x()][current.y()] = new Field(current.x(), current.y(), value);
            values[current.x()][current.y()].remove(value);
            if (calculateSolution(solution)) {
                if (!inList(solutions, solution)) solutions.add(solution);
                if(solutions.size() > 1) return false;
                //todo: optimize the values[][][] handle
                // so the same solution is never calculated more than once
            }
        }
        return true;
    }
}
