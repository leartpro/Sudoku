package controller;

import domain.Field;
import utils.Generator;
import utils.GridUtils;
import utils.Solver;

import java.util.List;

public class Controller extends GridUtils {
    private int[][] grid;
    private Generator generator;
    private Solver solver;

    public Controller() {
        this.grid = generatePuzzle(0);
        solver = new Solver(toComparableGrid(grid));
    }

    public Controller(int difficulty) {
        this.grid = generatePuzzle(difficulty);
        solver = new Solver(toComparableGrid(grid));
    }

    public int[][] generatePuzzle(int difficulty) {
        generator = new Generator(difficulty);
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
}
