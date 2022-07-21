package controller;

import domain.Field;
import utils.generator.Generator;
import utils.GridUtils;
import utils.solver.Solver;

public final class Controller extends GridUtils {
    private int[][] currentGrid, givenGrid;
    private final Generator generator;
    private final Solver solver;

    public Controller(ProgressMonitor progressMonitor) {
        this.currentGrid = new int[9][9];
        this.givenGrid = new int[9][9];
        this.generator = new Generator(progressMonitor);
        this.solver = new Solver();
    }

    public void generateNewPuzzle() {
        this.givenGrid = mapComparableGrid(generator.generate());
        this.currentGrid = newInstanceOf(givenGrid);
    }

    public boolean isSolved() {
        return isSolved(toComparableGrid(currentGrid));
    }

    public int[][] getGivenGrid() {
        return givenGrid;
    }

    public int[][] getCurrentGrid() {
        return currentGrid;
    }

    public int[][] solvedGrid() {
        return mapComparableGrid(solver.solve(toComparableGrid(givenGrid)));
    }

    public boolean isAvailable(int x, int y) {
        return givenGrid[x][y] == 0;
    }

    public void insert(int x, int y, int value) {
        currentGrid[x][y] = value;
    }

    private Field[][] toComparableGrid(int[][] grid) {
        Field[][] result = new Field[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                result[x][y] = new Field(x, y, grid[x][y]);
            }
        }
        return result;
    }

    private int[][] mapComparableGrid(Field[][] grid) {
        int[][] result = new int[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                result[x][y] = grid[x][y].value();
            }
        }
        return result;
    }

    public int[][] newInstanceOf(int[][] grid) {
        int[][] solution = new int[9][9];
        for (int x = 0; x < 9; x++) {
            System.arraycopy(grid[x], 0, solution[x], 0, 9);
        }
        return solution;
    }
}