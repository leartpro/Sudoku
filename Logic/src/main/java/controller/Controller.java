package controller;

import utils.Generator;
import utils.GridUtils;
import utils.Solver;

public final class Controller extends GridUtils {
    private final int[][] grid;
    private final Generator generator;
    private final Solver solver;
    private int difficulty;

    public Controller() {
        generator = new Generator();
        this.grid = generatePuzzle(0);
        solver = new Solver(toComparableGrid(grid));
        difficulty = 0;
    }

    public int[][] generatePuzzle(int difficulty) {
        generator.setDifficulty(difficulty);
        return mapComperableGrid(generator.generate());
    }

    public boolean isSolved() {
        return solver.isSolved();
    }

    public int[][] getGrid() {
        return grid;
    }

    public int[][] solvedGrid() {
        return addAll(grid, solver.solve());
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getDifficulty() {
        return difficulty;
    }
}