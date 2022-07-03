package utils;

import domain.Field;

import java.util.ArrayList;
import java.util.Arrays;
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
            assert (grid[xPos][yPos].value() == 0);
            if (isUnique(grid, new Field(xPos, yPos, value))) {
                grid[xPos][yPos] = new Field(xPos, yPos, value);
                if (calculateSolution(grid)) return true;
                else grid[xPos][yPos] = new Field(xPos, yPos, 0); // replace
            }
        }
        return false;
    }

    /*todo
    -first try with easy techniques than improve step by step
        to validate the difficulty of the given grid
     */
    public boolean isSolvable() {
        return calculateSolution(newInstanceOf(grid));
    }

    public List<Field[][]> allSolutions() {
        List<Field[][]> solutions = new ArrayList<>();
        List<Integer>[][] values = new ArrayList[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                values[x][y] = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
            }
        }
        boolean stillSolvable = true;
        while (stillSolvable) {
            Field[][] solution = newInstanceOf(grid);
            if (calculateOneSolution2(values, solution)) {
                solutions.add(solution);
            } else {
                stillSolvable = false;
            }
        }
        return solutions;
    }

    /*
    for each point try with a value that is different from the previous one
        then try to solve the sudoku
     */
    public List<Field[][]> allSolutions2() { //todo: find each solution twice
        List<Field[][]> solutions = new ArrayList<>();
        List<Integer>[][] values = new ArrayList[9][9];
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
        //available.forEach(System.out::print);
        //System.out.println();
        for (Field current : available) {
            //System.out.println(current);
            Field[][] solution = newInstanceOf(grid);
            Integer value = values[current.x()][current.y()].get(0);
            solution[current.x()][current.y()] = new Field(current.x(), current.y(), value);
            values[current.x()][current.y()].remove(value);
            if (calculateSolution(solution)) {
                if (!inList(solutions, solution)) solutions.add(solution); //todo: inList does not work
                //else System.out.println("This solution already exists!");
            }
        }
        return solutions;
    }

    private boolean calculateOneSolution2(List<Integer>[][] availableValues, Field[][] grid) {
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
        for (Integer value : new ArrayList<>(availableValues[xPos][yPos])) {
            assert (grid[xPos][yPos].value() == 0);
            if (isUnique(grid, new Field(xPos, yPos, value))) {
                grid[xPos][yPos] = new Field(xPos, yPos, value);
                availableValues[xPos][yPos].remove(value);
                if (calculateOneSolution(availableValues, grid)) {
                    return true;
                } else {
                    grid[xPos][yPos] = new Field(xPos, yPos, 0);
                    availableValues[xPos][yPos].add(value);
                }
            }
        }
        return false;
    }

    private boolean calculateOneSolution(List<Integer>[][] availableValues, Field[][] grid) { //todo duplicated code
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
        for (Integer value : new ArrayList<>(availableValues[xPos][yPos])) {
            assert (grid[xPos][yPos].value() == 0);
            if (isUnique(grid, new Field(xPos, yPos, value))) {
                grid[xPos][yPos] = new Field(xPos, yPos, value);
                availableValues[xPos][yPos].remove(value);
                if (calculateOneSolution(availableValues, grid)) {
                    return true;
                } else {
                    grid[xPos][yPos] = new Field(xPos, yPos, 0);
                    availableValues[xPos][yPos].add(value);
                }
            }
        }
        return false;
    }
}
