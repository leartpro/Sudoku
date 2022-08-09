package controller;

import domain.Field;
import utils.generator.Generator;
import utils.GridUtils;
import utils.solver.Solver;

import java.util.List;
import java.util.Random;

/**
 * Handels the Generator.java as well as Solver.java and stores the current-/given- grid
 */
public final class Controller extends GridUtils {
    private int[][] currentGrid, givenGrid;
    private final Generator generator;
    private Solver solver;
    private int difficulty;

    /**
     * @param progressMonitor progress-monitor to give the progress to
     */
    public Controller(ProgressMonitor progressMonitor) {
        this.currentGrid = new int[9][9];
        this.givenGrid = new int[9][9];
        this.difficulty = 1;
        this.generator = new Generator(progressMonitor);
        this.solver = new Solver(difficulty);
    }

    /**
     * @param difficulty set the difficulty
     */
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * @return the current difficulty
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * generates a new puzzle
     */
    public void generateNewPuzzle() {
        this.givenGrid = mapComparableGrid(generator.generate(this.difficulty));
        this.currentGrid = newInstanceOf(givenGrid);
    }

    /**
     * @return if the current-grid is solved correctly
     */
    public boolean isSolved() {
        return isSolved(toComparableGrid(currentGrid));
    }

    /**
     * @return the generated-grid
     */
    public int[][] getGivenGrid() {
        return givenGrid;
    }

    /**
     * @return the current-grid
     */
    public int[][] getCurrentGrid() {
        return currentGrid;
    }

    /**
     * @return the solution for the generated-grid
     */
    public int[][] solvedGrid() {
        this.solver = new Solver(this.difficulty);
        return mapComparableGrid(solver.solve(toComparableGrid(givenGrid)));
    }

    /**
     * @param x the row
     * @param y the column
     * @return true if this point is editable
     */
    public boolean isAvailable(int x, int y) {
        return givenGrid[x][y] == 0;
    }

    /**
     * @param x the row
     * @param y the column
     * @param value the value to insert
     */
    public void insert(int x, int y, int value) {
        currentGrid[x][y] = value;
    }

    /**
     * @param grid a grid as int[][]
     * @return the given grid as Field[][]
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
     * @param grid a grid as Field[][]
     * @return the given grid as int[][]
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
     * @param grid the int[][] to create a new instance from
     * @return a new instance of the given int[][]
     */
    private int[][] newInstanceOf(int[][] grid) {
        int[][] solution = new int[9][9];
        for (int x = 0; x < 9; x++) {
            System.arraycopy(grid[x], 0, solution[x], 0, 9);
        }
        return solution;
    }

    /**
     * complete one random field in the current-grid
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