package utils;

import domain.Field;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
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
    void isSolvedTrue() {
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
    void isSolvedFalse() { //TODO: complete grid
        solver = new Solver(
                toComparableGrid(
                        new int[][]{ //unsolved grid
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
                ));
        assertFalse(solver.isSolved());
    }

    @Test
    void solve() { //TODO: create multiple test methods for different difficulties of grids
        assertEquals(new ArrayList<>(
                        List.of( //TODO: complete expected output
                                new Field(0, 0, 0),
                                new Field(0, 0, 0)
                        )
                ), solver.solve()
        );
    }

    private Field[][] toComparableGrid(int[][] grid) {
        Field[][] result = new Field[9][9];
        for(int x = 0; x < 9; x++) {
            for(int y = 0; y < 9; y++) {
                result[x][y] = new Field(x, y, grid[x][y]);
            }
        }
        return result;
    }
}