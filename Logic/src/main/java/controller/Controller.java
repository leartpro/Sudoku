package controller;

import domain.Field;
import utils.Generator;
import utils.GridUtils;
import utils.Solver;

import java.util.List;

public final class Controller extends GridUtils {
    private int[][] grid;
    private final Generator generator;
    private final Solver solver;
    private int difficulty = 0;

    public Controller() {
        generator = new Generator(0);
        this.grid = generatePuzzle(0);
        solver = new Solver(toComparableGrid(grid));
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

    public int[][] solveGrid() {
        return addAll(grid, solver.solve());
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void pause() {
    }

    public void start() {
    }

    public void resume() {
    }
}
