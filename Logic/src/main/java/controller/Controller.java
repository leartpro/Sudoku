package controller;

import utils.generator.Generator;
import utils.GridUtils;
import utils.solver.Solver;

public final class Controller extends GridUtils {
    private int[][] currentGrid, givenGrid;
    private final Generator generator;
    private final Solver solver;

    public Controller() {
        this.currentGrid = new int[9][9];
        this.givenGrid = new int[9][9];
        this.generator = new Generator();
        this.solver = new Solver(toComparableGrid(currentGrid));
    }

    public void generateNewPuzzle() {
        this.givenGrid = mapComparableGrid(generator.generate());
        this.currentGrid = newInstanceOf(givenGrid);
    }

    public boolean isSolved() {
        return isSolved(toComparableGrid(currentGrid));
    }

    public int[][] getGivenGrid() { //TODO: 2getters for tow different grids (actual and given)
        return givenGrid;
    }

    public int[][] getCurrentGrid() { //TODO: 2getters for tow different grids (actual and given)
        return currentGrid;
    }

    public int[][] solvedGrid() {
        return addAll(newInstanceOf(givenGrid), solver.solve(toComparableGrid(givenGrid)));
    }

    public boolean isAvailable(int x, int y) {
        return givenGrid[x][y] == 0;
    }

    public void insert(int x, int y, int value) {
        currentGrid[x][y] = value;
    }
}