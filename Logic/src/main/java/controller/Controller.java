package controller;

import domain.Field;
import utils.Generator;
import utils.GridUtils;
import utils.Solver;

public class Controller extends GridUtils {
    private int[][] grid;
    private Generator generator;
    private Solver solver;
    public Controller(int difficulty) {
        this.grid = generatePuzzle(difficulty);
    }

    private int[][] generatePuzzle(int difficulty) {
        return null;
    }

    public boolean isSolved() {
        return new Solver(toComparableGrid(grid)).isSolved();
    }

    public int[][] getGrid() {
        return null;
    }

    public int[][] solveGrid() {
        return null;
    }
}
