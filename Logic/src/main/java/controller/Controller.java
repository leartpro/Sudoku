package controller;

import domain.Field;
import utils.generator.Generator;
import utils.GridUtils;
import utils.solver.Solver;

import java.util.List;
import java.util.Random;

/**
 *
 */
public final class Controller extends GridUtils {
    private int[][] currentGrid, givenGrid;
    private final Generator generator;
    private Solver solver;
    private int difficulty;

    /**
     * @param progressMonitor
     */
    public Controller(ProgressMonitor progressMonitor) {
        this.currentGrid = new int[9][9];
        this.givenGrid = new int[9][9];
        this.difficulty = 1;
        this.generator = new Generator(progressMonitor);
        this.solver = new Solver(difficulty);
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getDifficulty() {
        return difficulty;
    }

    /**
     *
     */
    public void generateNewPuzzle() {
        this.givenGrid = mapComparableGrid(generator.generate(this.difficulty));
        this.currentGrid = newInstanceOf(givenGrid);
    }

    /**
     * @return
     */
    public boolean isSolved() {
        return isSolved(toComparableGrid(currentGrid));
    }

    /**
     * @return
     */
    public int[][] getGivenGrid() {
        return givenGrid;
    }

    /**
     * @return
     */
    public int[][] getCurrentGrid() {
        return currentGrid;
    }

    /**
     * @return
     */
    public int[][] solvedGrid() {
        this.solver = new Solver(this.difficulty);
        return mapComparableGrid(solver.solve(toComparableGrid(givenGrid)));
    }

    /**
     * @param x
     * @param y
     * @return
     */
    public boolean isAvailable(int x, int y) {
        return givenGrid[x][y] == 0;
    }

    /**
     * @param x
     * @param y
     * @param value
     */
    public void insert(int x, int y, int value) {
        currentGrid[x][y] = value;
    }

    /**
     * @param grid
     * @return
     */
    private Field[][] toComparableGrid(int[][] grid) {
        Field[][] result = new Field[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                result[x][y] = new Field(x, y, grid[x][y]);
            }
        }
        return result;
    }

    /**
     * @param grid
     * @return
     */
    private int[][] mapComparableGrid(Field[][] grid) {
        int[][] result = new int[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                result[x][y] = grid[x][y].value();
            }
        }
        return result;
    }

    /**
     * @param grid
     * @return
     */
    private int[][] newInstanceOf(int[][] grid) {
        int[][] solution = new int[9][9];
        for (int x = 0; x < 9; x++) {
            System.arraycopy(grid[x], 0, solution[x], 0, 9);
        }
        return solution;
    }

    /**
     *
     */
    public void giveHint() {
        this.solver = new Solver(this.difficulty);
        List<Field> values = flatGrid(solver.solve(toComparableGrid(givenGrid)));
        values.removeIf(f -> currentGrid[f.x()][f.y()] != 0);
        Field selected = values.get(new Random().nextInt(values.size()));
        assert currentGrid[selected.x()][selected.y()] == selected.value();
        this.currentGrid[selected.x()][selected.y()] = selected.value();
    }
}