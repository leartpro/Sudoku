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
    void isSolvedTrue() { //TODO: complete grid
        solver = new Solver(
                toComparableGrid(
                        new int[][]{ //solved grid
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
    void solve() {
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