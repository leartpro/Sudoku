package utils;

import domain.Field;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SolverTest {
    private Field[][] grid;
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
        solver = new Solver(
                toComparableGrid(
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
                )
        );
        Field insert = new Field(1, 1, 6);
        assertTrue(solver.isUnique(insert));
    }

    @Test
    void isUniqueFalseTest() {
        solver = new Solver(
                toComparableGrid(
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
                )
        );
        Field insert = new Field(1, 1, 5);
        assertFalse(solver.isUnique(insert));
    }

    @Test
    void isUniqueInSquareTest() {
        solver = new Solver(
                toComparableGrid(
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
                )
        );
        Field insert = new Field(1, 1, 6);
        assertFalse(solver.isUnique(insert));
    }

    @Test
    void isSolvedTrueTest() {
        solver = new Solver(
                toComparableGrid(
                        new int[][]{ //solved grid
                                {8, 2, 7, 1, 5, 4, 3, 9, 6},
                                {9, 6, 5, 3, 2, 7, 1, 4, 8},
                                {3, 4, 1, 6, 8, 9, 7, 5, 2},
                                {5, 9, 3, 4, 6, 8, 2, 7, 1},
                                {4, 7, 2, 5, 1, 3, 6, 8, 9},
                                {6, 1, 8, 9, 7, 2, 4, 3, 5},
                                {7, 8, 6, 2, 3, 5, 9, 1, 4},
                                {1, 5, 4, 7, 9, 6, 8, 2, 3},
                                {2, 3, 9, 8, 4, 1, 5, 6, 7}
                        }
                )
        );
        assertTrue(solver.isSolved());
    }

    @Test
    void isSolvedFalseTest() {
        solver = new Solver(
                toComparableGrid(
                        new int[][]{ //unsolved grid
                                {8, 2, 7, 1, 5, 0, 3, 9, 6},
                                {9, 0, 5, 3, 2, 7, 1, 4, 8},
                                {3, 4, 1, 6, 8, 9, 7, 5, 2},
                                {5, 9, 3, 4, 6, 8, 2, 0, 1},
                                {4, 7, 2, 5, 1, 3, 6, 8, 9},
                                {6, 1, 8, 9, 0, 2, 4, 3, 5},
                                {7, 8, 6, 2, 3, 5, 9, 1, 4},
                                {1, 0, 4, 7, 9, 6, 8, 2, 3},
                                {2, 3, 9, 8, 4, 1, 5, 6, 7}
                        }
                ));
        assertFalse(solver.isSolved());
    }

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

        if(solver.isSolved()) System.out.println("correctly solved");
        else System.out.println("unsolved or incorrect");

        boolean success = true;
        for(int x = 0; x < 9; x++) {
            for(int y = 0; y < 9; y++) {
                try {
                    assertEquals(expected[x][y], actual[x][y]);
                } catch (AssertionFailedError e) {
                    System.err.println("Unexpected value="+actual[x][y]+" on x="+x+", y="+y+". Should be value="+expected[x][y]);
                    success = false;
                }
            }
        }
        if(!success) System.err.flush();
        System.out.println();

        if(!success) {
            StringBuilder output = new StringBuilder();
            output.append("Expected:              Actual:"+"\r\n");
            for(int x = 0; x < 9; x++) {
                for(int y = 0; y < 9; y++) {
                    output.append(expected[x][y]).append(" ");
                }
                output.append("     ");
                for(int y = 0; y < 9; y++) {
                    output.append(actual[x][y]).append(" ");
                }
                output.append("\r\n");
            }
            System.out.println(output);
        }
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

    private int[][] addAll(int[][] grid, List<Field> inserts) {
        inserts.forEach(i -> {
            if(grid[i.point().x()][i.point().y()] == 0) grid[i.point().x()][i.point().y()] = i.value();
        });
        return grid;
    }
}