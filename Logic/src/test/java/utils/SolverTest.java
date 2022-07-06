package utils;

import domain.Field;
import org.junit.jupiter.api.Test;
import utils.solver.Solver;

import static org.junit.jupiter.api.Assertions.*;

class SolverTest extends GridUtils {
    private Solver solver;

    /*
    Grid pattern:
                            new int[][]{
                                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                                {0, 0, 0, 0, 0, 0, 0, 0, 0}
                            }
     */

    @Test
    void isUniqueTrueTest() {
        Field[][] grid = toComparableGrid(
                new int[][]{ //solved grid
                        {8, 2, 7, 1, 5, 4, 3, 9, 6},
                        {9, 0/*place in*/, 5, 3, 2, 7, 1, 4, 8},
                        {3, 4, 1, 6, 8, 9, 7, 5, 2},
                        {5, 9, 3, 4, 6, 8, 2, 7, 1},
                        {4, 7, 2, 5, 1, 3, 6, 8, 9},
                        {6, 1, 8, 9, 7, 2, 4, 3, 5},
                        {7, 8, 6, 2, 3, 5, 9, 1, 4},
                        {1, 5, 4, 7, 9, 6, 8, 2, 3},
                        {2, 3, 9, 8, 4, 1, 5, 6, 7}
                }
        );
        solver = new Solver(grid);
        Field insert = new Field(1, 1, 6);
        assertTrue(solver.isUnique(grid, insert));
    }

    @Test
    void isUniqueFalseTest() {
        Field[][] grid = toComparableGrid(
                new int[][]{ //solved grid
                        {8, 2, 7, 1, 5, 4, 3, 9, 6},
                        {9, 0/*place in*/, 5, 3, 2, 7, 1, 4, 8},
                        {3, 4, 1, 6, 8, 9, 7, 5, 2},
                        {5, 9, 3, 4, 6, 8, 2, 7, 1},
                        {4, 7, 2, 5, 1, 3, 6, 8, 9},
                        {6, 1, 8, 9, 7, 2, 4, 3, 5},
                        {7, 8, 6, 2, 3, 5, 9, 1, 4},
                        {1, 5, 4, 7, 9, 6, 8, 2, 3},
                        {2, 3, 9, 8, 4, 1, 5, 6, 7}
                }
        );
        solver = new Solver(grid);
        Field insert = new Field(1, 1, 5);
        assertFalse(solver.isUnique(grid, insert));
    }

    @Test
    void isUniqueInSquareFalseTest() {
        Field[][] grid = toComparableGrid(
                new int[][]{ //solved grid
                        {8, 2, 7, 1, 5, 4, 3, 9, 6},
                        {9, 0/*place in*/, 5, 3, 2, 7, 1, 4, 8},
                        {3, 4, 6/*1*/, 6, 8, 9, 7, 5, 2},
                        {5, 9, 3, 4, 6, 8, 2, 7, 1},
                        {4, 7, 2, 5, 1, 3, 6, 8, 9},
                        {6, 1, 8, 9, 7, 2, 4, 3, 5},
                        {7, 8, 6, 2, 3, 5, 9, 1, 4},
                        {1, 5, 4, 7, 9, 6, 8, 2, 3},
                        {2, 3, 9, 8, 4, 1, 5, 6, 7}
                }
        );
        solver = new Solver(grid);
        Field insert = new Field(1, 1, 6);
        assertFalse(solver.isUnique(grid, insert));
    }

    @Test
    void isUniqueInSquareTrueTest() {
        Field[][] grid = toComparableGrid(
                new int[][]{ //unsolved
                        {4, 0, 0, 0, 9, 1, 0, 0, 0},
                        {0, 0, 9, 0, 0, 7, 4, 2, 5},
                        {0, 5, 8, 3, 4, 0, 1, 9, 0},
                        {6, 9, 1, 0, 0, 0, 0, 0, 0},
                        {0, 0, 3, 9, 6, 4, 7, 0, 0},
                        {0, 0, 0, 0, 0, 0, 9, 6, 3},
                        {0, 8, 7, 0, 2, 6, 5, 3, 0},
                        {3, 1, 5, 8, 0, 0, 6, 0, 0},
                        {0, 0, 0, 1, 5, 0, 0, 0, 9}
                }
        );
        solver = new Solver(grid);
        Field insert = new Field(0, 1, 1);
        assertFalse(solver.isUnique(grid, insert));
    }

    @Test
    void soleCandidates() {
        Field[][] actual = toComparableGrid(
                new int[][]{
                        {0, 0, 0, 0, 0, 1, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 6, 0, 0, 0},
                        {0, 0, 0, 4, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 8, 0, 0, 0, 0},
                        {2, 0, 9, 0, 0, 0, 0, 0, 7},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 3, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0}
                }
        );
        Field[][] expected = toComparableGrid(
                new int[][]{
                        {0, 0, 0, 0, 0, 1, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 6, 0, 0, 0},
                        {0, 0, 0, 4, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 8, 0, 0, 0, 0},
                        {2, 0, 9, 0, 0, 0/**/, 0, 0, 7},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 3, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0}
                }
        );
        solver = new Solver(actual);
        solver.soleCandidates();
        assertEquals(new Field(5, 5, 5), compareTo(actual, expected).get(0));
    }

    @Test
    void uniqueCandidates() {
        Field[][] actual = toComparableGrid(
                new int[][]{
                        {0, 0, 4, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 4, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {5, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 4, 0, 0, 0}
                }
        );
        Field[][] expected = toComparableGrid(
                new int[][]{
                        {0, 0, 4, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 4, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {5, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0/**/, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 4, 0, 0, 0}
                }
        );
        solver = new Solver(actual);
        solver.uniqueCandidates();
        displaySmall(actual);
        assertEquals(new Field(7, 0, 4), compareTo(actual, expected).get(0));
    }

    /*
    @Test
    void solveEasyTest() { //TODO: create multiple test methods for different difficulties of grids
        int[][] input = new int[][]{ //unsolved
                {4, 0, 0, 0, 9, 1, 0, 0, 0},
                {0, 0, 9, 0, 0, 7, 4, 2, 5},
                {0, 5, 8, 3, 4, 0, 1, 9, 0},
                {6, 9, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 3, 9, 6, 4, 7, 0, 0},
                {0, 0, 0, 0, 0, 0, 9, 6, 3},
                {0, 8, 7, 0, 2, 6, 5, 3, 0},
                {3, 1, 5, 8, 0, 0, 6, 0, 0},
                {0, 0, 0, 1, 5, 0, 0, 0, 9}
        };
        int[][] expected = new int[][]{ //solution
                {4, 6, 2, 5, 9, 1, 3, 8, 7},
                {1, 3, 9, 6, 8, 7, 4, 2, 5},
                {7, 5, 8, 3, 4, 2, 1, 9, 6},
                {6, 9, 1, 7, 3, 8, 2, 5, 4},
                {5, 2, 3, 9, 6, 4, 7, 1, 8},
                {8, 7, 4, 2, 1, 5, 9, 6, 3},
                {9, 8, 7, 4, 2, 6, 5, 3, 1},
                {3, 1, 5, 8, 7, 9, 6, 4, 2},
                {2, 4, 6, 1, 5, 3, 8, 7, 9}
        };
        solver = new Solver(toComparableGrid(input));
        int[][] actual = addAll(input, solver.solve());
        if (new Solver(toComparableGrid(actual)).isSolved()) System.out.println("correctly solved");
        else System.out.println("unsolved or incorrect");

        boolean success = true;
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                try {
                    assertEquals(expected[x][y], actual[x][y]);
                } catch (AssertionFailedError e) {
                    System.err.println("Unexpected value=" + actual[x][y] + " on x=" + x + ", y=" + y + ". Should be value=" + expected[x][y]);
                    success = false;
                }
            }
        }
        if (!success) System.err.flush();
        System.out.println();

        if (!success) {
            StringBuilder output = new StringBuilder();
            output.append("Expected:              Actual:" + "\r\n");
            for (int x = 0; x < 9; x++) {
                for (int y = 0; y < 9; y++) {
                    output.append(expected[x][y]).append(" ");
                }
                output.append("     ");
                for (int y = 0; y < 9; y++) {
                    output.append(actual[x][y]).append(" ");
                }
                output.append("\r\n");
            }
            System.out.println(output);
        } else {
            displaySmall(toComparableGrid(actual));
            display(toComparableGrid(actual));
        }
    }

    @Test
    void allSolutions2Easy() {
        Field[][] grid = toComparableGrid(
                new int[][]{ //unsolved grid
                        {2, 9, 5, 7, 4, 3, 8, 6, 1},
                        {4, 3, 1, 8, 6, 5, 9, 0, 0},
                        {8, 7, 6, 1, 9, 2, 5, 4, 3},
                        {3, 8, 7, 4, 5, 9, 2, 1, 6},
                        {6, 1, 2, 3, 8, 7, 4, 9, 5},
                        {5, 4, 9, 2, 1, 6, 7, 3, 8},
                        {7, 6, 3, 5, 3, 4, 1, 8, 9},
                        {9, 2, 8, 6, 7, 1, 3, 5, 4},
                        {1, 5, 4, 9, 3, 8, 6, 0, 0}
                }
        );
        solver = new Solver(grid);
        List<Field[][]> solutions = new ArrayList<>(solver.allSolutions());
        assertEquals(2, solutions.size());

        StringBuilder output = new StringBuilder();
        output.append("""
                Input:                 Solution/s:
                """);
        int count = 0;
        for (Field[][] solution : solutions) {
            for (int x = 0; x < 9; x++) {
                if (count == 0) {
                    for (int y = 0; y < 9; y++) {
                        output.append(grid[x][y].value()).append(" ");
                    }
                } else {
                    output.append("  ".repeat(9));
                }
                output.append("     ");
                for (int y = 0; y < 9; y++) {
                    output.append(solution[x][y].value()).append(" ");
                }
                output.append("\r\n");
            }
            output.append("\r\n");
            count++;
        }
        System.out.println(output);
    }

    @Test
    void allSolutions2Medium() {
        Field[][] grid = toComparableGrid(
                new int[][]{ //unsolved grid
                        {0, 6, 0, 0, 0, 0, 0, 0, 0 },
                        {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 5, 0, 0, 0, 0, 0, 9, 0},
                        {0, 0, 0, 0, 3, 0, 0, 0, 0},
                        {5, 0, 3, 0, 0, 0, 0, 0, 8},
                        {0, 7, 4, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 2, 6, 0, 0, 0},
                        {3, 0, 0, 0, 0, 0, 0, 0, 2},
                        {0, 0, 0, 0, 5, 0, 0, 0, 9}
                }
        );

        solver = new Solver(grid);
        List<Field[][]> solutions = new ArrayList<>(solver.allSolutions());
        assertEquals(44, solutions.size());
        StringBuilder output = new StringBuilder();
        output.append("""
                Input:                 Solution/s:
                """);
        int count = 0;
        for (Field[][] solution : solutions) {
            for (int x = 0; x < 9; x++) {
                if (count == 0) {
                    for (int y = 0; y < 9; y++) {
                        output.append(grid[x][y].value()).append(" ");
                    }
                } else {
                    output.append("  ".repeat(9));
                }
                output.append("     ");
                for (int y = 0; y < 9; y++) {
                    output.append(solution[x][y].value()).append(" ");
                }
                output.append("\r\n");
            }
            output.append("\r\n");
            count++;
        }
        System.out.println(output);
    }
    */
}