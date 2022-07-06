package controller;

import utils.generator.Generator;
import utils.GridUtils;
import utils.solver.Solver;

public final class Controller extends GridUtils {
    private int[][] grid;
    private final Generator generator;
    private final Solver solver;

    public Controller() {
        this.grid = new int[9][9];
        this.generator = new Generator();
        this.solver = new Solver(toComparableGrid(grid));
    }

    public void generateNewPuzzle() {
        this.grid = mapComparableGrid(generator.generate());
    }

    public boolean isSolved() {
        return solver.isSolved(toComparableGrid(grid));
    }

    public int[][] getGrid() {
        return grid;
    }

    public int[][] solvedGrid() {
        return addAll(grid, solver.solve());
    }
}